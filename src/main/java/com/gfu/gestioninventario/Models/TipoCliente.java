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


    // Relación inversa con Cliente
    @OneToMany(mappedBy = "tipoCliente")
    private List<Cliente> clientes;

    //CONSTRUCTORES
    //DEFAULT
    public TipoCliente() {
    }
    //CON ATRIBUTOS


    public TipoCliente(List<Cliente> clientes, Integer tipoClienteId) {
        this.clientes = clientes;
        this.tipoClienteId = tipoClienteId;
    }

    //GETTERS AND SETTERS
    public Integer getTipoClienteId() {
        return tipoClienteId;
    }
    public void setTipoClienteId(Integer tipoClienteId) {
        this.tipoClienteId = tipoClienteId;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

}
