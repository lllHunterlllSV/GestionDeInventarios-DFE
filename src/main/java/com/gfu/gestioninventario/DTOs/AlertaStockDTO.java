package com.gfu.gestioninventario.DTOs;

public class AlertaStockDTO {
    private Integer productoId;
    private String producto;
    private Long stockTotal;
    private String categoria;


    // Constructor, getters y setters

    public AlertaStockDTO(Integer productoId, String producto, Long stockTotal, String categoria) {
        this.productoId = productoId;
        this.producto = producto;
        this.stockTotal = stockTotal;
        this.categoria = categoria;

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

    public Long getStockTotal() {
        return stockTotal;
    }

    public void setStockTotal(Long stockTotal) {
        this.stockTotal = stockTotal;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }
}