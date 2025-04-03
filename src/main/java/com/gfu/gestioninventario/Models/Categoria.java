package com.gfu.gestioninventario.Models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoria_id")
    private Integer id;

    @Column(name = "categoria", nullable = false, length = 50)
    private String nombre;

    //Relacion con la tabla productos
    @OneToMany(mappedBy = "categoria")
    private List<Producto> productos;




    public Categoria() {

    }

    public Categoria(Integer id, List<Producto> productos, String nombre) {
        this.id = id;
        this.productos = productos;
        this.nombre = nombre;
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
    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public Categoria(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

}
