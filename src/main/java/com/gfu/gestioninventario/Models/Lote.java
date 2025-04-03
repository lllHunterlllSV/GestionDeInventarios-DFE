package com.gfu.gestioninventario.Models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "lotes")
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lote_id")
    private Integer loteId;

    @ManyToOne
    @JoinColumn(name = "orden_id", nullable = false)
    private OrdenCompra orden;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "fecha_ingreso")
    private Date fechaIngreso;

    @Column(name = "fecha_vencimiento")
    private Date fechaVencimiento;

    @Column(name = "costo_unitario")
    private Double costoUnitario;

    @Column(name = "estado")
    private Boolean estado; // true = Activo, false = Inactivo

    // Constructor vacío
    public Lote() {
    }

    // Constructor con parámetros
    public Lote(OrdenCompra orden, Producto producto, Integer cantidad, Date fechaIngreso, Date fechaVencimiento, Double costoUnitario, Boolean estado) {
        this.orden = orden;
        this.producto = producto;
        this.cantidad = cantidad;
        this.fechaIngreso = fechaIngreso;
        this.fechaVencimiento = fechaVencimiento;
        this.costoUnitario = costoUnitario;
        this.estado = estado;
    }

    // Getters y Setters
    public Integer getLoteId() {
        return loteId;
    }

    public void setLoteId(Integer loteId) {
        this.loteId = loteId;
    }

    public OrdenCompra getOrden() {
        return orden;
    }

    public void setOrden(OrdenCompra orden) {
        this.orden = orden;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Double getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(Double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}