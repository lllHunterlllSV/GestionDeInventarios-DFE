package com.gfu.gestioninventario.DTOs;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;

public class VentaCompletaDTO {

    private Integer clienteId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaVenta;
    private Integer usuarioId;
    private Integer tipoMovimientoId; // Siempre será 2 para salida
    private List<DetalleVentaDTO> detalles;

    // Getters y Setters
    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getTipoMovimientoId() {
        return tipoMovimientoId;
    }

    public void setTipoMovimientoId(Integer tipoMovimientoId) {
        this.tipoMovimientoId = tipoMovimientoId;
    }

    public List<DetalleVentaDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentaDTO> detalles) {
        this.detalles = detalles;
    }

    // Clase interna para los detalles de la venta


    public static class DetalleVentaDTO {
        private Integer productoId;
        private Integer loteId;
        private Integer cantidad;
        private BigDecimal precioUnitario; // ✅ Agregado

        // Getters y Setters
        public Integer getProductoId() {
            return productoId;
        }

        public void setProductoId(Integer productoId) {
            this.productoId = productoId;
        }

        public Integer getLoteId() {
            return loteId;
        }

        public void setLoteId(Integer loteId) {
            this.loteId = loteId;
        }

        public Integer getCantidad() {
            return cantidad;
        }

        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }

        public BigDecimal getPrecioUnitario() {
            return precioUnitario;
        }

        public void setPrecioUnitario(BigDecimal precioUnitario) {
            this.precioUnitario = precioUnitario;
        }
    }

}
