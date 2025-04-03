package com.gfu.gestioninventario.Models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "devoluciones_compras")
public class DevolucionCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "devolucionCompra_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "orden_id", nullable = false)
    private OrdenCompra ordenCompra;

    @ManyToOne
    @JoinColumn(name = "detalle_id", nullable = false)
    private DetalleOrdenCompra detalleOrdenCompra;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuarios usuario;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "motivo", nullable = false, length = 255)
    private String motivo;

    @Column(name = "fecha_devolucion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDevolucion;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    public DevolucionCompra(Integer id, OrdenCompra ordenCompra, DetalleOrdenCompra detalleOrdenCompra, Usuarios usuario, Integer cantidad, String motivo, Date fechaDevolucion, String estado) {
        this.id = id;
        this.ordenCompra = ordenCompra;
        this.detalleOrdenCompra = detalleOrdenCompra;
        this.usuario = usuario;
        this.cantidad = cantidad;
        this.motivo = motivo;
        this.fechaDevolucion = fechaDevolucion;
        this.estado = estado;
    }

    public DevolucionCompra() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrdenCompra getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenCompra ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public DetalleOrdenCompra getDetalleOrdenCompra() {
        return detalleOrdenCompra;
    }

    public void setDetalleOrdenCompra(DetalleOrdenCompra detalleOrdenCompra) {
        this.detalleOrdenCompra = detalleOrdenCompra;
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
