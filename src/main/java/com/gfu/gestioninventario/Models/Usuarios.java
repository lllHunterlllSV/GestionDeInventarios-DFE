package com.gfu.gestioninventario.Models;

import jakarta.persistence.*;

import java.util.Date;
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
  /// mapeo a ventas
  /// mapeo a devoluciones

    //constructor


    public Usuarios() {
    }

    public Usuarios(Integer usuario_id, Date created_at, String email, String nombreCompleto, String usuario, String contrasena) {
        this.usuario_id = usuario_id;
        this.created_at = created_at;
        this.email = email;
        this.nombreCompleto = nombreCompleto;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    /// getters y setters

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
}
