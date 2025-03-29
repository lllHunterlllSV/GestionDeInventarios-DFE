package com.gfu.gestioninventario.Models;
import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class Clientes {

    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clienteId;
    private String nombreCliente;
    private Integer tipoClienteId;
    private String NIF;
    private String DUI;
    private Boolean estado;
    private String telefono;
    private String email;

    //CONSTRUCTORES
    //DEFAULT
    public Clientes() {
    }
    //CON ATRIBUTOS
    public Clientes(Integer clienteId, String nombreCliente,
                    Integer tipoClienteId, String NIF, String DUI,
                    Boolean estado, String telefono, String email) {
        this.clienteId = clienteId;
        this.nombreCliente = nombreCliente;
        this.tipoClienteId = tipoClienteId;
        this.NIF = NIF;
        this.DUI = DUI;
        this.estado = estado;
        this.telefono = telefono;
        this.email = email;
    }

    //GETTERS AND SETTERS
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
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
