package com.gfu.gestioninventario.Models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "devoluciones")
public class Devolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "devolucion_id")
    private Integer devolucionId;

    //relacion devolucion a venta
    @ManyToOne
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta ventas;
//relacion detalle venta
    @ManyToOne
    @JoinColumn(name = "detalleVenta_id", nullable = false)
    private DetalleVenta detalleVenta;
//relacion usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuarios usuario;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "motivo")
    private String motivo;

    @Column(name = "fecha_devolucion")
    private Date fechaDevolucion;

    @Column(name = "estado")
    private String estado; // "Solicitada", "Aprobada", "Rechazada"

    // Constructor vacío
    public Devolucion() {
    }

    // Constructor con parámetros


    public Devolucion(Integer devolucionId, String estado, Date fechaDevolucion, String motivo, Integer cantidad, Usuarios usuario, DetalleVenta detalleVenta, Venta ventas) {
        this.devolucionId = devolucionId;
        this.estado = estado;
        this.fechaDevolucion = fechaDevolucion;
        this.motivo = motivo;
        this.cantidad = cantidad;
        this.usuario = usuario;
        this.detalleVenta = detalleVenta;
        this.ventas = ventas;
    }

    // Getters y Setters
    public Integer getDevolucionId() {
        return devolucionId;
    }

    public void setDevolucionId(Integer devolucionId) {
        this.devolucionId = devolucionId;
    }

    public Venta getVentas() {
        return ventas;
    }

    public void setVentas(Venta ventas) {
        this.ventas = ventas;
    }

    public DetalleVenta getDetalleVenta() {
        return detalleVenta;
    }

    public void setDetalleVenta(DetalleVenta detalleVenta) {
        this.detalleVenta = detalleVenta;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}