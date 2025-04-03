package com.gfu.gestioninventario.Models;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ordenes_compra")
public class OrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orden_id")
    private Integer ordenId;
/// relacion proveedor
    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedores proveedor;

    @Column(name = "fecha_orden")
    private Date fechaOrden;

    @Column(name = "estado")
    private String estado; // Ej: "Pendiente", "Recibida", "Cancelada"
/// Relacion detalle
    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleOrdenCompra> detalles;

    /// Relacion devolucion de compra
    @OneToMany(mappedBy = "OrdenCompra")
    private List<DevolucionCompra> devolucionesCompra;

    /// Relacion lote
    @OneToMany(mappedBy = "orden")
    private List<Lote> lotes;

    // Constructor vacío (requerido por JPA)
    public OrdenCompra() {
    }

    // Constructor con parámetros


    public OrdenCompra(Integer ordenId, List<Lote> lotes, List<DevolucionCompra> devolucionesCompra, List<DetalleOrdenCompra> detalles, String estado, Date fechaOrden, Proveedores proveedor) {
        this.ordenId = ordenId;
        this.lotes = lotes;
        this.devolucionesCompra = devolucionesCompra;
        this.detalles = detalles;
        this.estado = estado;
        this.fechaOrden = fechaOrden;
        this.proveedor = proveedor;
    }

    // Getters y Setters
    public Integer getOrdenId() {
        return ordenId;
    }

    public void setOrdenId(Integer ordenId) {
        this.ordenId = ordenId;
    }

    public Proveedores getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedores proveedor) {
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

    public List<DevolucionCompra> getDevolucionesCompra() {
        return devolucionesCompra;
    }

    public void setDevolucionesCompra(List<DevolucionCompra> devolucionesCompra) {
        this.devolucionesCompra = devolucionesCompra;
    }

    public List<Lote> getLotes() {
        return lotes;
    }

    public void setLotes(List<Lote> lotes) {
        this.lotes = lotes;
    }
}