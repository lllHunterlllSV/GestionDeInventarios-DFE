package Models;

import java.util.Date;
import java.util.List;

public class OrdenCompra {
    private Integer ordenId;
    private Proveedor proveedor;
    private Date fechaOrden;
    private String estado; // "Pendiente", "Recibida", "Cancelada"
    private List<DetalleOrdenCompra> detalles;

    // Constructor vacío
    public OrdenCompra() {
    }

    // Constructor con parámetros
    public OrdenCompra(Proveedor proveedor, Date fechaOrden, String estado) {
        this.proveedor = proveedor;
        this.fechaOrden = fechaOrden;
        this.estado = estado;
    }

    // Getters y Setters
    public Integer getOrdenId() {
        return ordenId;
    }

    public void setOrdenId(Integer ordenId) {
        this.ordenId = ordenId;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Date getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(Date fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<DetalleOrdenCompra> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleOrdenCompra> detalles) {
        this.detalles = detalles;
    }
}