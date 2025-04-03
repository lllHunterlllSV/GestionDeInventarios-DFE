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
    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "cliente_id", nullable = false)
    private Cliente clienteId; // Relación con Cliente
    @Column(name = "usuario_id")
    private Integer usuarioId; // Después se agregará la relación con usuarios
    @Column(name = "fecha_venta")
    private Date fechaVenta;
    @Column(name = "total")
    private Double total;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "notas")
    private String notas;
    @OneToMany(mappedBy = "venta") // Relación con DetalleVenta
    private List<DetalleVenta> detalleVentas;

    //CONSTRUCTORES
    //DEFAULT
    public Venta() {
    }

    //CON ATRIBUTOS
    public Venta(Integer ventaId, String numeroFactura,
                 Cliente clienteId, Integer usuarioId,
                 Date fechaVenta, Double total, Boolean estado, String notas) {
        this.ventaId = ventaId;
        this.numeroFactura = numeroFactura;
        this.clienteId = clienteId;
        this.usuarioId = usuarioId;
        this.fechaVenta = fechaVenta;
        this.total = total;
        this.estado = estado;
        this.notas = notas;
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

    public Cliente getClienteId() {
        return clienteId;
    }

    public void setClienteId(Cliente clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
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
}
