package com.gfu.gestioninventario.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_movimiento")
public class TipoMovimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipomovimiento_id")
    private Integer id;

    @Column(name = "movimiento", nullable = false, length = 50)
    private String movimiento;

    public TipoMovimiento(Integer id, String movimiento) {
        this.id = id;
        this.movimiento = movimiento;
    }

    public TipoMovimiento() {}

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
}
