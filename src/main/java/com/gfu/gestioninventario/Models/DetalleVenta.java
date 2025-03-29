package com.gfu.gestioninventario.Models;
import jakarta.persistence.*;

@Entity
@Table(name = "detalle_ventas")
public class DetalleVenta {

    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer detalleVentaId;
    private Integer ventaId;
    private Integer productoId;
    private Integer cantidad;
    private Double precioUnitario;

    //CONSTRUCTORES
    //DEFAULT
    public DetalleVenta() {
    }

    //CON ATRIBUTOS
    public DetalleVenta(Integer detalleVentaId, Integer ventaId,
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

    public Integer getVentaId() {
        return ventaId;
    }

    public void setVentaId(Integer ventaId) {
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
