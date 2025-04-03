package com.gfu.gestioninventario.Models;
import jakarta.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "tipoCliente_id", referencedColumnName = "tipoCliente_id", nullable = false)
    private TipoCliente tipoCliente; // Relación con TipoCliente

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
}
