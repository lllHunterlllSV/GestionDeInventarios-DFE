package com.gfu.gestioninventario.DTOs;

public class TipoClienteDTO {
    private Integer tipoClienteId;
    private String tipoCliente;

    // Constructor por defecto
    public TipoClienteDTO() {
    }

    // Constructor para la creación
    public TipoClienteDTO(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    // Constructor completo
    public TipoClienteDTO(Integer tipoClienteId, String tipoCliente) {
        this.tipoClienteId = tipoClienteId;
        this.tipoCliente = tipoCliente;
    }

    // Getters y Setters
    public Integer getTipoClienteId() {
        return tipoClienteId;
    }

    public void setTipoClienteId(Integer tipoClienteId) {
        this.tipoClienteId = tipoClienteId;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
}