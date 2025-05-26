package com.gfu.gestioninventario.DTOs;

public class ProductoDTO {
    private Integer productoId;
    private String nombre;
    private String descripcion;
    private String categoria;
    private String medida;
    private String proveedor;

    public ProductoDTO() {}

    // Getters y Setters
    public Integer getProductoId() { return productoId; }
    public void setProductoId(Integer productoId) { this.productoId = productoId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getMedida() { return medida; }
    public void setMedida(String medida) { this.medida = medida; }

    public String getProveedor() { return proveedor; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }
}
