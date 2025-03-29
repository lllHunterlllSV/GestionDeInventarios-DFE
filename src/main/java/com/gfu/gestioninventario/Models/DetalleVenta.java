package com.gfu.gestioninventario.Models;
import jakarta.persistence.*;

@Entity
@Table(name = "detalle_ventas")
public class DetalleVenta {

    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detalleVenta_id")
    private Integer detalleVentaId;

    @ManyToOne
    @JoinColumn(name = "venta_id", referencedColumnName = "venta_id", nullable = false) // Relación con Venta
    private Venta ventaId;

    @Column(name = "producto_id") // Después se agregará la relación con Producto
    private Integer productoId;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precio_unitario")
    private Double precioUnitario;

    //CONSTRUCTORES
    //DEFAULT
    public DetalleVenta() {
    }

    //CON ATRIBUTOS
    public DetalleVenta(Integer detalleVentaId, Venta ventaId,
                        Integer productoId, Integer cantidad, Double precioUnitario) {
        this.detalleVentaId = detalleVentaId;
        this.ventaId = ventaId;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    // GETTERS AND SETTERS
    public Integer getDetalleVentaId() {
        return detalleVentaId;
    }

    public void setDetalleVentaId(Integer detalleVentaId) {
        this.detalleVentaId = detalleVentaId;
    }

    public Venta getVentaId() {
        return ventaId;
    }

    public void setVentaId(Venta ventaId) {
        this.ventaId = ventaId;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
