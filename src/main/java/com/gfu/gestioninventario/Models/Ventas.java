package com.gfu.gestioninventario.Models;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "ventas")
public class Ventas {

    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ventaId;
    private String numeroFactura;
    private Integer clienteId;
    private Integer usuarioId;
    private Date fechaVenta;
    private Double total;
    private Boolean estado;
    private String notas;

    //CONSTRUCTORES
    //DEFAULT
    public Ventas() {
    }

    //CON ATRIBUTOS
    public Ventas(Integer ventaId, String numeroFactura,
                  Integer clienteId, Integer usuarioId,
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

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
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
}
