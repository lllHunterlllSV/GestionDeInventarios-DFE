package com.gfu.gestioninventario.Services;

import com.gfu.gestioninventario.DTOs.VentaCompletaDTO;
import com.gfu.gestioninventario.Models.Venta;
import com.gfu.gestioninventario.Repository.VentaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

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

}
