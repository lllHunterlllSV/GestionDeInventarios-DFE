package com.gfu.gestioninventario.Models;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ventas")
public class Venta {

    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venta_id")
    private Integer ventaId;
    @Column(name = "numeroFactura")
    private String numeroFactura;

    // Relación con Cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "cliente_id", nullable = false)
    private Cliente cliente;
   //relacion con usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id", nullable = false)
    private Usuarios usuarioId;
    // Relación con DetalleVenta
    @OneToMany(mappedBy = "venta")
    private List<DetalleVenta> detalleVentas;

    //relacion con devoluciones
    @OneToMany(mappedBy = "ventas")
    private List<Devolucion> devoluciones;

    @Column(name = "fecha_venta")
    private Date fechaVenta;
    @Column(name = "total")
    private Double total;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "notas")
    private String notas;



    //CONSTRUCTORES
    //DEFAULT
    public Venta() {
    }

    //CON ATRIBUTOS


    public Venta(Integer ventaId, String notas, Boolean estado, Double total, Date fechaVenta, List<Devolucion> devoluciones, List<DetalleVenta> detalleVentas, Usuarios usuarioId, Cliente cliente, String numeroFactura) {
        this.ventaId = ventaId;
        this.notas = notas;
        this.estado = estado;
        this.total = total;
        this.fechaVenta = fechaVenta;
        this.devoluciones = devoluciones;
        this.detalleVentas = detalleVentas;
        this.usuarioId = usuarioId;
        this.cliente = cliente;
        this.numeroFactura = numeroFactura;
    }

    //GETTERS AND SETTERS
    public Integer getVentaId() {
        return ventaId;
    }

    public void setVentaId(Integer ventaId) {
        this.ventaId = ventaId;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }



    public Usuarios getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuarios usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public List<DetalleVenta> getDetalleVentas() {
        return detalleVentas;
    }

    public void setDetalleVentas(List<DetalleVenta> detalleVentas) {
        this.detalleVentas = detalleVentas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Devolucion> getDevoluciones() {
        return devoluciones;
    }

    public void setDevoluciones(List<Devolucion> devoluciones) {
        this.devoluciones = devoluciones;
    }
}
