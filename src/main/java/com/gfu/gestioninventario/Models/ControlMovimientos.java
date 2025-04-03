package com.gfu.gestioninventario.Models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "control_movimientos")
public class ControlMovimientos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movimiento_id")
    private Integer id;
/// relacion lote
    @ManyToOne
    @JoinColumn(name = "lote_id", nullable = false)
    private Lote lote;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "fecha_movimiento", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaMovimiento;

    /// relacion usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuarios usuario;
///relacion tipo
    @ManyToOne
    @JoinColumn(name = "tipomovimiento_id", nullable = false)
    private TipoMovimiento tipoMovimiento;




    public ControlMovimientos(Integer id, Lote lote, Integer cantidad, Date fechaMovimiento, Usuarios usuario, TipoMovimiento tipoMovimiento) {
        this.id = id;
        this.lote = lote;
        this.cantidad = cantidad;
        this.fechaMovimiento = fechaMovimiento;
        this.usuario = usuario;
        this.tipoMovimiento = tipoMovimiento;
    }

    public ControlMovimientos() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(Date fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

}
