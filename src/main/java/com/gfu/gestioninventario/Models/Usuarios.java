package com.gfu.gestioninventario.Models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuarios")

public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer usuario_id;
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "contraseña")
    private String contrasena;
    @Column(name = "nombreCompleto")
    private String nombreCompleto;
    @Column(name="email")
    private String email;
    @Column(name="created_at")
    private Date created_at;

    /// mapeo a tabla roles
  @ManyToOne(fetch = FetchType.EAGER)
  private Roles roles;

  /// mapeo a tabla control de movimiento
  @OneToMany(mappedBy = "usuario")
  private Set<ControlMovimientos> controlMovimientosSet;
  /// mapeo a ventas
  @OneToMany(mappedBy = "usuarioId")
  private List<Venta> ventas;
  /// mapeo a devoluciones
  @OneToMany (mappedBy = "usuario")
  private List<Devolucion> devoluciones;

  ///realcaion devoluciones de compra
  @OneToMany (mappedBy = "usuario")
  private List<DevolucionCompra> devolucionCompra;

    //constructor


    public Usuarios() {
    }

    public Usuarios(List<DevolucionCompra> devolucionCompra, List<Devolucion> devoluciones, List<Venta> ventas, Set<ControlMovimientos> controlMovimientosSet, Roles roles, Date created_at, String email, String nombreCompleto, String usuario, Integer usuario_id, String contrasena) {
        this.devolucionCompra = devolucionCompra;
        this.devoluciones = devoluciones;
        this.ventas = ventas;
        this.controlMovimientosSet = controlMovimientosSet;
        this.roles = roles;
        this.created_at = created_at;
        this.email = email;
        this.nombreCompleto = nombreCompleto;
        this.usuario = usuario;
        this.usuario_id = usuario_id;
        this.contrasena = contrasena;
    }

    /// getters y setters
    ///


    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Integer usuario_id) {
        this.usuario_id = usuario_id;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public List<DevolucionCompra> getDevolucionCompra() {
        return devolucionCompra;
    }

    public void setDevolucionCompra(List<DevolucionCompra> devolucionCompra) {
        this.devolucionCompra = devolucionCompra;
    }

    public List<Devolucion> getDevoluciones() {
        return devoluciones;
    }

    public void setDevoluciones(List<Devolucion> devoluciones) {
        this.devoluciones = devoluciones;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }

    public Set<ControlMovimientos> getControlMovimientosSet() {
        return controlMovimientosSet;
    }

    public void setControlMovimientosSet(Set<ControlMovimientos> controlMovimientosSet) {
        this.controlMovimientosSet = controlMovimientosSet;
    }
}
