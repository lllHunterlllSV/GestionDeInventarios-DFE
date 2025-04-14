package com.gfu.gestioninventario.DTOs;

public class ClienteDTO {
    private Integer clienteId;
    private String nombreCliente;
    private Integer tipoClienteId;
    private String tipoClienteNombre;
    private String NIF;
    private String DUI;
    private Boolean estado;
    private Integer telefono;
    private String email;

    // Constructor por defecto
    public ClienteDTO() {
    }

    // Constructor para la creación
    public ClienteDTO(String nombreCliente, Integer tipoClienteId, String NIF, String DUI,
                      Boolean estado, Integer telefono, String email) {
        this.nombreCliente = nombreCliente;
        this.tipoClienteId = tipoClienteId;
        this.NIF = NIF;
        this.DUI = DUI;
        this.estado = estado;
        this.telefono = telefono;
        this.email = email;
    }

    // Constructor completo
    public ClienteDTO(Integer clienteId, String nombreCliente, Integer tipoClienteId,
                      String tipoClienteNombre, String NIF, String DUI, Boolean estado,
                      Integer telefono, String email) {
        this.clienteId = clienteId;
        this.nombreCliente = nombreCliente;
        this.tipoClienteId = tipoClienteId;
        this.tipoClienteNombre = tipoClienteNombre;
        this.NIF = NIF;
        this.DUI = DUI;
        this.estado = estado;
        this.telefono = telefono;
        this.email = email;
    }

    // Getters y Setters
    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Integer getTipoClienteId() {
        return tipoClienteId;
    }

    public void setTipoClienteId(Integer tipoClienteId) {
        this.tipoClienteId = tipoClienteId;
    }

    public String getTipoClienteNombre() {
        return tipoClienteNombre;
    }

    public void setTipoClienteNombre(String tipoClienteNombre) {
        this.tipoClienteNombre = tipoClienteNombre;
    }

    public String getNIF() {
        return NIF;
    }

    public void setNIF(String NIF) {
        this.NIF = NIF;
    }

    public String getDUI() {
        return DUI;
    }

    public void setDUI(String DUI) {
        this.DUI = DUI;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}