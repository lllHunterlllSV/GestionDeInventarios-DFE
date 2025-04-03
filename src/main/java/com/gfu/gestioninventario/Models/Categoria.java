package com.gfu.gestioninventario.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoria_id")
    private Integer id;

    @Column(name = "categoria", nullable = false, length = 50)
    private String nombre;

    public Categoria(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Categoria() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
