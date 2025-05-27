package com.gfu.gestioninventario.DTOs;

public class StockDTO {
    private Integer id;
    private Integer productoId;
    private String productoNombre;
    private Integer stock;
    private String nombreProveedor;
    private String categoria;



    public StockDTO() {
    }

    public StockDTO(Integer id, String nombreProveedor, String categoria, String productoNombre, Integer productoId, Integer stock) {
        this.id = id;
        this.nombreProveedor = nombreProveedor;
        this.categoria = categoria;
        this.productoNombre = productoNombre;
        this.productoId = productoId;
        this.stock = stock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getProductoNombre() {
        return productoNombre;
    }

    public void setProductoNombre(String productoNombre) {
        this.productoNombre = productoNombre;
    }
}
