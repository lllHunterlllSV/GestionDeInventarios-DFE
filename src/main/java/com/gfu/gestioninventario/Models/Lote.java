package com.gfu.gestioninventario.Models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "lotes")
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lote_id")
    private Integer loteId;
///relacion orden compra
    @ManyToOne
    @JoinColumn(name = "orden_id", nullable = false)
    private OrdenCompra orden;
/// relacion con productos
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;
/// control de movimientos
    @OneToMany(mappedBy = "lote")
    private Set<ControlMovimientos> controlMovimientosSet;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "fecha_ingreso")
    private Date fechaIngreso;

    @Column(name = "fecha_vencimiento")
    private Date fechaVencimiento;

    @Column(name = "costo_unitario")
    private BigDecimal costoUnitario;

    @Column(name = "estado")
    private Boolean estado; // true = Activo, false = Inactivo

    // Constructor vacío
    public Lote() {
    }

    // Constructor con parámetros


    public Lote(Integer loteId, OrdenCompra orden, Set<ControlMovimientos> controlMovimientosSet, Producto producto, Integer cantidad, Date fechaIngreso, Date fechaVencimiento, Boolean estado, BigDecimal costoUnitario) {
        this.loteId = loteId;
        this.orden = orden;
        this.controlMovimientosSet = controlMovimientosSet;
        this.producto = producto;
        this.cantidad = cantidad;
        this.fechaIngreso = fechaIngreso;
        this.fechaVencimiento = fechaVencimiento;
        this.estado = estado;
        this.costoUnitario = costoUnitario;
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

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Set<ControlMovimientos> getControlMovimientosSet() {
        return controlMovimientosSet;
    }

    public void setControlMovimientosSet(Set<ControlMovimientos> controlMovimientosSet) {
        this.controlMovimientosSet = controlMovimientosSet;
    }
}