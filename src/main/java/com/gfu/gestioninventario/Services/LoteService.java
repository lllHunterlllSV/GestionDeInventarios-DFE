package com.gfu.gestioninventario.Services;

import com.gfu.gestioninventario.Models.DetalleOrdenCompra;
import com.gfu.gestioninventario.Models.Lote;
import com.gfu.gestioninventario.Models.Proveedores;
import com.gfu.gestioninventario.Repository.LoteRepository;
import com.gfu.gestioninventario.Repository.OrdenCompraRepository;
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

    public void registrarLoteCompleto(Integer proveedorId, Date fechaOrden, List<DetalleOrdenCompra> detalles,
                                      Date fechaVencimientoGeneral, Integer usuarioId, Integer tipoMovimientoId) {
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
                            ordenId, productoId, cantidad, new Date(), fechaVencimientoGeneral, precio, usuarioId, tipoMovimientoId
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
            System.err.println("\n\n");
            throw new RuntimeException("Error en registrarLoteCompleto: " + ex.getMessage(), ex);
        }
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
