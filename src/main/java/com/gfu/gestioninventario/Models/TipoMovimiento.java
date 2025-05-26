package com.gfu.gestioninventario.Models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "tipo_movimiento")
public class TipoMovimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipomovimiento_id")
    private Integer id;

    @Column(name = "movimiento", nullable = false, length = 50)
    private String movimiento;
   ///  relacion control movimiento
   @OneToMany(mappedBy = "tipoMovimiento")
   private Set<ControlMovimientos> controlMovimientosSet;

    public TipoMovimiento(Integer id, Set<ControlMovimientos> controlMovimientosSet, String movimiento) {
        this.id = id;
        this.controlMovimientosSet = controlMovimientosSet;
        this.movimiento = movimiento;
    }

    public TipoMovimiento() {}

    public TipoMovimiento(int i) {
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<ControlMovimientos> getControlMovimientosSet() {
        return controlMovimientosSet;
    }

    public void setControlMovimientosSet(Set<ControlMovimientos> controlMovimientosSet) {
        this.controlMovimientosSet = controlMovimientosSet;
    }
}
