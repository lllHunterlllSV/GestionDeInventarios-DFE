package com.gfu.gestioninventario.Services;

import com.gfu.gestioninventario.Models.*;
import com.gfu.gestioninventario.Repository.*;
import jakarta.transaction.Transactional;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class LoteService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @Autowired
    private DetalleOrdenCompraRepository detalleOrdenRepository;

    @Autowired
    private ControlMovimientosRepository controlMovimientosRepository;

    @Autowired
    private TipoMovimientoRepository tipoMovimientoRepository;


    public void registrarLoteCompleto(Integer proveedorId, Date fechaOrden, List<DetalleOrdenCompra> detalles,
                                      Integer usuarioId, Integer tipoMovimientoId) {
        try {
            Integer ordenId = jdbcTemplate.queryForObject(
                    "EXEC sp_CrearOrdenCompra @proveedor_id = ?, @fecha_orden = ?, @estado = ?",
                    new Object[]{proveedorId, fechaOrden, "PENDIENTE"},
                    Integer.class
            );

            if (ordenId == null) {
                throw new RuntimeException("No se pudo generar la orden de compra.");
            }

            System.out.println("ORDEN ID GENERADO: " + ordenId);

            for (DetalleOrdenCompra detalle : detalles) {
                Integer productoId = null;
                Integer cantidad = null;
                try {
                    productoId = detalle.getProducto().getProductoId();
                    cantidad = detalle.getCantidad();
                    BigDecimal precio = detalle.getPrecioUnitario();
                    Date fechaVencimiento = detalle.getFechaVencimiento();

                    System.out.println("Insertando detalle: productoId=" + productoId + ", cantidad=" + cantidad + ", precio=" + precio);

                    int filasDetalle = jdbcTemplate.update(
                            "EXEC sp_AgregarDetalleOrden ?, ?, ?, ?",
                            ordenId, productoId, cantidad, precio
                    );

                    if (filasDetalle == 0) {
                        System.err.println("⚠ ADVERTENCIA: No se insertó el detalle para productoId=" + productoId);
                    }

                    int filasLote = jdbcTemplate.update(
                            "EXEC sp_RegistrarLote ?, ?, ?, ?, ?, ?, ?, ?",
                            ordenId, productoId, cantidad, new Date(), fechaVencimiento, precio, usuarioId, tipoMovimientoId
                    );

                    if (filasLote == 0) {
                        System.err.println("⚠ ADVERTENCIA: No se insertó el lote para productoId=" + productoId);
                    }

                    System.out.println("✔ Detalle insertado: " + filasDetalle + ", Lote insertado: " + filasLote);
                } catch (Exception e) {
                    System.err.println("\n\n");
                    System.err.println("════════════════════════════════════════");
                    System.err.println("❌❌❌ ERROR DE INSERCIÓN DE DETALLE/LOTE ❌❌❌");
                    System.err.println("Datos: productoId=" + productoId + ", cantidad=" + cantidad);
                    System.err.println("Excepción: " + e.getClass().getName());
                    System.err.println("Mensaje: " + e.getMessage());
                    System.err.println("════════════════════════════════════════");
                    e.printStackTrace(System.err);
                    System.err.println("\n\n");
                }
            }

        } catch (Exception ex) {
            System.err.println("\n\n");
            System.err.println("═══════════════════════════════════════════════════");
            System.err.println("❌❌❌ ERROR CRÍTICO EN registrarLoteCompleto ❌❌❌");
            System.err.println("Proveedor ID: " + proveedorId + " | Fecha orden: " + fechaOrden);
            System.err.println("Excepción: " + ex.getClass().getName());
            System.err.println("Mensaje: " + ex.getMessage());
            System.err.println("═══════════════════════════════════════════════════");
            ex.printStackTrace(System.err);
            throw new RuntimeException("Error en registrarLoteCompleto: " + ex.getMessage(), ex);
        }
    }


    @Transactional
    public void editarOrdenCompra(Integer ordenId, Date nuevaFechaOrden, List<DetalleOrdenCompra> nuevosDetalles,
                                  Integer usuarioId, Integer tipoMovimientoId,
                                  String accionFinal) {

        OrdenCompra orden = ordenCompraRepository.findById(ordenId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

     /*   if (!EstadoOrden.PENDIENTE.equals(orden.getEstado())) {
            throw new IllegalStateException("Solo se pueden editar órdenes pendientes");
        }*/

        // Eliminar movimientos anteriores
        for (Lote lote : orden.getLotes()) {
            controlMovimientosRepository.deleteByLoteId(lote.getLoteId());
        }

        // Eliminar detalles y lotes anteriores
        detalleOrdenRepository.deleteByOrdenId(ordenId);
        loteRepository.deleteByOrdenId(ordenId);

        // Actualizar fecha
        orden.setFechaOrden(nuevaFechaOrden);

        TipoMovimiento tipoMovimiento = tipoMovimientoRepository.findById(tipoMovimientoId)
                .orElseThrow(() -> new RuntimeException("Tipo de movimiento no encontrado"));

        for (DetalleOrdenCompra nuevoDetalle : nuevosDetalles) {
            nuevoDetalle.setOrden(orden);
            detalleOrdenRepository.save(nuevoDetalle);

            Lote nuevoLote = new Lote();
            nuevoLote.setOrden(orden);
            nuevoLote.setProducto(nuevoDetalle.getProducto());
            nuevoLote.setCantidad(nuevoDetalle.getCantidad());
            nuevoLote.setCostoUnitario(nuevoDetalle.getPrecioUnitario());
            nuevoLote.setFechaIngreso(new Date());
            nuevoLote.setFechaVencimiento(nuevoDetalle.getFechaVencimiento());

            // Estado del lote según acción
            boolean activo = !"CANCELAR".equalsIgnoreCase(accionFinal);
            nuevoLote.setEstado(activo);

            loteRepository.save(nuevoLote);

            ControlMovimientos movimiento = new ControlMovimientos();
            movimiento.setLote(nuevoLote);
            movimiento.setCantidad(nuevoDetalle.getCantidad());
            movimiento.setFechaMovimiento(new Date());
            movimiento.setUsuario(null); // O usuarioId si lo quieres usar
            movimiento.setTipoMovimiento(tipoMovimiento);
            controlMovimientosRepository.save(movimiento);
        }

        // Cambiar estado de la orden si se indicó acción
        if ("RECIBIR".equalsIgnoreCase(accionFinal)) {
            orden.setEstado(EstadoOrden.RECIBIDO);
        } else if ("CANCELAR".equalsIgnoreCase(accionFinal)) {
            orden.setEstado(EstadoOrden.CANCELADO);
        }

        ordenCompraRepository.save(orden);
    }








    public void ajustarLotePorEdicion(Integer ordenId, Integer productoId, Integer nuevaCantidad, Integer usuarioId, Integer tipoMovimientoId) {
        Optional<Lote> optionalLote = loteRepository.findByOrdenOrdenIdAndProductoProductoId(ordenId, productoId);

        if (optionalLote.isPresent()) {
            Lote lote = optionalLote.get();
            Integer cantidadActual = lote.getCantidad();

            Integer totalSalidas = jdbcTemplate.queryForObject(
                    "SELECT ISNULL(SUM(cantidad), 0) FROM control_movimientos WHERE lote_id = ? AND tipomovimiento_id = 2",
                    new Object[]{lote.getLoteId()}, Integer.class);

            int diferencia = cantidadActual - nuevaCantidad;

            if (diferencia == 0) return; // No hay cambios

            // Si hay diferencia y ya hubo salidas, solo registrar movimiento
            if (totalSalidas > 0 || diferencia < 0) {
                jdbcTemplate.update("INSERT INTO control_movimientos (lote_id, cantidad, fecha_movimiento, usuario_id, tipomovimiento_id) VALUES (?, ?, GETDATE(), ?, ?)",
                        lote.getLoteId(), Math.abs(diferencia), usuarioId, tipoMovimientoId);
            } else {
                // Aún se puede ajustar lote directamente
                jdbcTemplate.update("UPDATE lotes SET cantidad = ? WHERE lote_id = ?",
                        nuevaCantidad, lote.getLoteId());
            }
        }
    }

    public List<Lote> listarPorOrden(Integer ordenId) {
        return loteRepository.findByOrdenOrdenId(ordenId);
    }






}
