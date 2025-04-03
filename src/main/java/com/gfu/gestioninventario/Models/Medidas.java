package com.gfu.gestioninventario.Models;



import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Medidas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Esto indica que el medida_id es autoincremental
    private Integer medidaId;

    @Column(nullable = false, length = 30)
    private String medida;

    @OneToMany(mappedBy ="medidas")
    private Set<Producto> producto;

    // Getters y setters

    public Integer getMedidaId() {
        return medidaId;
    }

    public void setMedidaId(Integer medidaId) {
        this.medidaId = medidaId;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public Set<Producto> getProducto() {
        return producto;
    }

    public void setProducto(Set<Producto> producto) {
        this.producto = producto;
    }

    // Constructor sin parámetros
    public Medidas() {
    }

    // Constructor con parámetros

    public Medidas(String medida, Integer medidaId, Set<Producto> producto) {
        this.medida = medida;
        this.medidaId = medidaId;
        this.producto = producto;
    }
}
