package com.gfu.gestioninventario.Models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "detalle_ordenCompra")
public class DetalleOrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detalle_id")
    private Integer detalleId;
//relacion orden de compra
    @ManyToOne
    @JoinColumn(name = "orden_id", nullable = false)
    private OrdenCompra orden;
//relacion productos
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column( name ="precio_unitario", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal precioUnitario;
/// relacion devoluciones de compra
     @OneToMany(mappedBy = "detalleOrdenCompra")
      private List<DevolucionCompra>devoluciones;




    // Constructor vacío
    public DetalleOrdenCompra() {
    }

    // Constructor con parámetros
    public DetalleOrdenCompra(OrdenCompra orden, Producto producto, Integer cantidad, BigDecimal precioUnitario) {
        this.orden = orden;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    // Getters y Setters
    public Integer getDetalleId() {
        return detalleId;
    }

    public void setDetalleId(Integer detalleId) {
        this.detalleId = detalleId;
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

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;

    }

    public List<DevolucionCompra> getDevoluciones() {
        return devoluciones;
    }

    public void setDevoluciones(List<DevolucionCompra> devoluciones) {
        this.devoluciones = devoluciones;
    }

    @Transient
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaVencimiento;



    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

}