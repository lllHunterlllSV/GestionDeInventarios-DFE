package com.gfu.gestioninventario.Models;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente {

    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "cliente_id")
    private Integer clienteId;

    @Column(name = "nombreCliente")
    private String nombreCliente;
    // Relación con TipoCliente
    @ManyToOne
    @JoinColumn(name = "tipoCliente_id", referencedColumnName = "tipoCliente_id", nullable = false)
    private TipoCliente tipoCliente;
    //Relacion con venta
    @OneToMany(mappedBy = "cliente")
    private List<Venta> ventas;


    @Column(name = "NIF")
    private String NIF;

    @Column(name = "DUI")
    private String DUI;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "email")
    private String email;

    //CONSTRUCTORES
    //DEFAULT
    public Cliente() {
    }
    //CON ATRIBUTOS


    public Cliente(Integer clienteId, String email, String telefono, Boolean estado, String DUI, String NIF, List<Venta> ventas, TipoCliente tipoCliente, String nombreCliente) {
        this.clienteId = clienteId;
        this.email = email;
        this.telefono = telefono;
        this.estado = estado;
        this.DUI = DUI;
        this.NIF = NIF;
        this.ventas = ventas;
        this.tipoCliente = tipoCliente;
        this.nombreCliente = nombreCliente;
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

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
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

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }
}
