package com.gfu.gestioninventario.DTOs;

import com.gfu.gestioninventario.Models.DetalleOrdenCompra;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class OrdenCompletaDTO {

    private Integer proveedorId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaOrden;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaVencimiento;

    private Integer usuarioId;

    private Integer tipoMovimientoId;

    private List<DetalleOrdenCompra> detalles;

    public Integer getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Integer proveedorId) {
        this.proveedorId = proveedorId;
    }

    public Date getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(Date fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getTipoMovimientoId() {
        return tipoMovimientoId;
    }

    public void setTipoMovimientoId(Integer tipoMovimientoId) {
        this.tipoMovimientoId = tipoMovimientoId;
    }

    public List<DetalleOrdenCompra> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleOrdenCompra> detalles) {
        this.detalles = detalles;
    }
}
