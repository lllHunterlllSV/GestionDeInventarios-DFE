package com.gfu.gestioninventario.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_cliente")
public class TipoCliente {

    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tipoClienteId;
    private String tipoCliente;

    //CONSTRUCTORES
    //DEFAULT
    public TipoCliente() {
    }
    //CON ATRIBUTOS
    public TipoCliente(Integer tipoClienteId, String tipoCliente) {
        this.tipoClienteId = tipoClienteId;
        this.tipoCliente = tipoCliente;
    }

    //GETTERS AND SETTERS
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
