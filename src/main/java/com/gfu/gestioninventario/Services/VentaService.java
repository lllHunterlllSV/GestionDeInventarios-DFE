    package com.gfu.gestioninventario.Services;

    import com.gfu.gestioninventario.DTOs.VentaCompletaDTO;
    import com.gfu.gestioninventario.Models.*;
    import com.gfu.gestioninventario.Repository.*;
    import jakarta.transaction.Transactional;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.jdbc.core.JdbcTemplate;
    import org.springframework.stereotype.Service;

    import java.math.BigDecimal;
    import java.util.Date;
    import java.util.List;
    import java.util.Map;

    @Service
    public class VentaService {

        @Autowired
        private VentaRepository ventaRepository;
        @Autowired
        private LoteRepository loteRepository;
        @Autowired
        private ControlMovimientosRepository controlMovimientosRepository;
        @Autowired
        private DetalleVentaRepository detalleVentaRepository;
        @Autowired
        private ProductoRepository productoRepository;
        @Autowired
        private TipoMovimientoRepository tipoMovimientoRepository;
        @Autowired
        private UsuariosRepository usuarioRepository;
        @Autowired
        private ClienteRepository clienteRepository;


        public Page<Venta> listarVentas(Pageable pageable) {
            return ventaRepository.findAll(pageable);
        }

        public Page<Venta> buscarPorKeyword(String keyword, Pageable pageable) {
            return ventaRepository.buscarPorKeyword(keyword, pageable);
        }

        public Venta buscarPorId(Integer id) {
            return ventaRepository.findById(id).orElse(null);
        }

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @Transactional
        public void registrarVentaCompleta(Integer clienteId, Date fechaVenta, List<VentaCompletaDTO.DetalleVentaDTO> detalles,
                                           Integer usuarioId, Integer tipoMovimientoId) {
            try {
                // 🔢 1. Calcular total
                BigDecimal total = detalles.stream()
                        .map(d -> d.getPrecioUnitario().multiply(BigDecimal.valueOf(d.getCantidad())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                // 🧾 2. Insertar cabecera de venta
                Integer ventaId = jdbcTemplate.queryForObject(
                        "EXEC sp_CrearVenta @cliente_id = ?, @fecha_venta = ?, @estado = ?, @total = ?, @notas = ?",
                        new Object[]{clienteId, fechaVenta, "REALIZADA", total, "Venta registrada desde módulo web"},
                        Integer.class
                );

                if (ventaId == null) {
                    throw new RuntimeException("No se pudo obtener el ID de la venta.");
                }

                System.out.println("VENTA ID GENERADO: " + ventaId);

                // 🧾 3. Recorrer detalles y registrar
                for (VentaCompletaDTO.DetalleVentaDTO detalle : detalles) {
                    Integer productoId = detalle.getProductoId();
                    Integer loteId = detalle.getLoteId();
                    Integer cantidad = detalle.getCantidad();

                    try {
                        // Detalle de venta
                        int filasDetalle = jdbcTemplate.update(
                                "EXEC sp_AgregarDetalleVenta ?, ?, ?, ?",
                                ventaId, productoId, loteId, cantidad
                        );

                        // Movimiento tipo salida
                        int filasMovimiento = jdbcTemplate.update(
                                "EXEC sp_RegistrarMovimiento ?, ?, ?, ?, ?",
                                loteId, cantidad, new Date(), usuarioId, tipoMovimientoId
                        );

                        if (filasDetalle == 0) {
                            System.err.println("⚠ No se insertó el detalle de venta para productoId=" + productoId);
                        }
                        if (filasMovimiento == 0) {
                            System.err.println("⚠ No se registró el movimiento de salida para loteId=" + loteId);
                        }

                        System.out.println("✔ Detalle y movimiento registrados correctamente para productoId=" + productoId);

                    } catch (Exception e) {
                        System.err.println("\n❌ Error en productoId=" + productoId + ", loteId=" + loteId);
                        e.printStackTrace(System.err);
                    }
                }

            } catch (Exception ex) {
                System.err.println("\n❌ Error crítico al registrar venta completa");
                ex.printStackTrace(System.err);
                throw new RuntimeException("Error en registrarVentaCompleta: " + ex.getMessage(), ex);
            }
        }

        @Transactional
        public void eliminarVentaCompleta(Integer ventaId) {
            Venta venta = ventaRepository.findById(ventaId)
                    .orElseThrow(() -> new RuntimeException("La venta no existe"));

            if (!venta.getEstado()) {
                throw new RuntimeException("Solo puede eliminar ventas en estado PENDIENTE");
            }

            // Restaurar stock de los lotes
            for (DetalleVenta detalle : venta.getDetalleVentas()) {
                Lote lote = detalle.getLote();
                lote.setCantidad(lote.getCantidad() + detalle.getCantidad());
                loteRepository.save(lote);

                // Eliminar movimientos de salida (tipoMovimientoId = 2)
                List<ControlMovimientos> movimientos = controlMovimientosRepository.findByLoteAndTipoMovimiento_Id(lote, 2);
                controlMovimientosRepository.deleteAll(movimientos);
            }

            // Eliminar los detalles de venta
            detalleVentaRepository.deleteAll(venta.getDetalleVentas());

            // Finalmente eliminar la venta
            ventaRepository.delete(venta);
        }

        public List<Map<String, Object>> obtenerDetalleParaEdicion(Integer ventaId) {
            return jdbcTemplate.queryForList("""
        SELECT p.nombre AS producto, d.producto_id, d.cantidad, d.precio_unitario, d.lote_id, l.fecha_vencimiento
        FROM detalle_ventas d
        JOIN productos p ON d.producto_id = p.producto_id
        JOIN lotes l ON d.lote_id = l.lote_id
        WHERE d.venta_id = ?
    """, ventaId);
        }



        @Transactional
        public void editarVenta(Integer ventaId, Date nuevaFechaVenta, Integer clienteId, Integer usuarioId,
                                Integer tipoMovimientoId, List<VentaCompletaDTO.DetalleVentaDTO> nuevosDetalles) {

            Venta venta = ventaRepository.findById(ventaId)
                    .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

            if (!Boolean.TRUE.equals(venta.getEstado())) {
                throw new IllegalStateException("Solo se pueden editar ventas activas");
            }

            // Revertir el stock y eliminar los movimientos anteriores
            for (DetalleVenta detalle : venta.getDetalleVentas()) {
                Lote lote = detalle.getLote();

                // Revertir cantidad al lote
                lote.setCantidad(lote.getCantidad() + detalle.getCantidad());
                loteRepository.save(lote);

                // Eliminar movimiento tipo SALIDA asociado
                controlMovimientosRepository.deleteByLoteAndTipoMovimiento_Id(lote, tipoMovimientoId); // tipoMovimientoId == 2
            }

            // Eliminar todos los detalles anteriores
            detalleVentaRepository.deleteByVentaId(ventaId);

            // Actualizar datos de la venta
            venta.setFechaVenta(nuevaFechaVenta);
            venta.setCliente(clienteRepository.findById(clienteId)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado")));
            venta.setTotal(0.0); // Se recalculará más abajo

            TipoMovimiento tipoMovimiento = tipoMovimientoRepository.findById(tipoMovimientoId)
                    .orElseThrow(() -> new RuntimeException("Tipo de movimiento no encontrado"));
            Usuarios usuario = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            double totalVenta = 0.0;

            for (VentaCompletaDTO.DetalleVentaDTO dto : nuevosDetalles) {
                Producto producto = productoRepository.findById(dto.getProductoId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                Lote lote = loteRepository.findById(dto.getLoteId())
                        .orElseThrow(() -> new RuntimeException("Lote no encontrado"));

                // Validar stock disponible
                if (lote.getCantidad() < dto.getCantidad()) {
                    throw new IllegalStateException("Stock insuficiente para el producto: " + producto.getNombre());
                }

                // Descontar del lote (salida)
                lote.setCantidad(lote.getCantidad() - dto.getCantidad());
                loteRepository.save(lote);

                // Crear nuevo detalle de venta
                DetalleVenta nuevoDetalle = new DetalleVenta();
                nuevoDetalle.setVenta(venta);
                nuevoDetalle.setProducto(producto);
                nuevoDetalle.setLote(lote);
                nuevoDetalle.setCantidad(dto.getCantidad());
                nuevoDetalle.setPrecioUnitario(dto.getPrecioUnitario());
                detalleVentaRepository.save(nuevoDetalle);

                // Crear nuevo movimiento
                ControlMovimientos movimiento = new ControlMovimientos();
                movimiento.setLote(lote);
                movimiento.setCantidad(dto.getCantidad());
                movimiento.setFechaMovimiento(new Date());
                movimiento.setUsuario(usuario);
                movimiento.setTipoMovimiento(tipoMovimiento);
                controlMovimientosRepository.save(movimiento);

                totalVenta += dto.getPrecioUnitario().doubleValue() * dto.getCantidad();
            }

            // Actualizar el total final de la venta
            venta.setTotal(totalVenta);
            ventaRepository.save(venta);
        }








    }
