package com.gfu.gestioninventario.Models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tipo_cliente")
public class TipoCliente {

    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipoCliente_id")
    private Integer tipoClienteId;

    @Column(name = "tipoCliente")
    private String tipoCliente;

    // Relación inversa con Cliente
    @OneToMany(mappedBy = "tipoCliente")
    private List<Cliente> clientes;

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
    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
}
