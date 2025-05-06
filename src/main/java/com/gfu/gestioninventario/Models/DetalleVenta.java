package com.gfu.gestioninventario.Models;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "detalle_ventas")
public class DetalleVenta {

    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detalleVenta_id")
    private Integer detalleVentaId;
/// relacion venta
    @ManyToOne
    @JoinColumn(name = "venta_id", referencedColumnName = "venta_id", nullable = false) // Relación con Venta
    private Venta venta;
/// relacion producto
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    ///  relacion devolucion
    @OneToMany(mappedBy = "detalleVenta")
    private List<Devolucion> devoluciones;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;

    //CONSTRUCTORES
    //DEFAULT
    public DetalleVenta() {
    }

    //CON ATRIBUTOS


    public DetalleVenta(Integer detalleVentaId, BigDecimal precioUnitario, Integer cantidad, List<Devolucion> devoluciones, Producto producto, Venta venta) {
        this.detalleVentaId = detalleVentaId;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.devoluciones = devoluciones;
        this.producto = producto;
        this.venta = venta;
    }

    // GETTERS AND SETTERS
    public Integer getDetalleVentaId() {
        return detalleVentaId;
    }

    public void setDetalleVentaId(Integer detalleVentaId) {
        this.detalleVentaId = detalleVentaId;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
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

    public List<Devolucion> getDevoluciones() {
        return devoluciones;
    }

    public void setDevoluciones(List<Devolucion> devoluciones) {
        this.devoluciones = devoluciones;
    }
}
