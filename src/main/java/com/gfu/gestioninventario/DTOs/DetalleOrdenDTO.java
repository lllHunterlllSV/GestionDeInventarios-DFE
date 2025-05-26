package com.gfu.gestioninventario.DTOs;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class DetalleOrdenDTO {

    private String proveedor;
    private Integer proveedorId; // ✅ nuevo
    private Date fechaOrden;
    private List<ItemDetalleDTO> detalles;

    // Getters y Setters
    public String getProveedor() { return proveedor; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }

    public Integer getProveedorId() { return proveedorId; }
    public void setProveedorId(Integer proveedorId) { this.proveedorId = proveedorId; }

    public Date getFechaOrden() { return fechaOrden; }
    public void setFechaOrden(Date fechaOrden) { this.fechaOrden = fechaOrden; }

    public List<ItemDetalleDTO> getDetalles() { return detalles; }
    public void setDetalles(List<ItemDetalleDTO> detalles) { this.detalles = detalles; }

    public static class ItemDetalleDTO {
        private Integer productoId;  // ✅ nuevo
        private String producto;
        private Integer cantidad;
        private BigDecimal precioUnitario;
        private String fechaVencimiento;

        public Integer getProductoId() { return productoId; }
        public void setProductoId(Integer productoId) { this.productoId = productoId; }

        public String getProducto() { return producto; }
        public void setProducto(String producto) { this.producto = producto; }

        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

        public BigDecimal getPrecioUnitario() { return precioUnitario; }
        public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

        public String getFechaVencimiento() { return fechaVencimiento; }
        public void setFechaVencimiento(String fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
    }
}
