package com.gfu.gestioninventario.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Objects; 
import java.util.Set;


@Entity
@Table(name = "usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer usuario_id;

    @NotBlank(message = "El nombre de usuario no puede estar vacio.")
    @Size(max = 10, message = "El nombre de usuario no puede exceder 10 caracteres.")
    @Column(name = "usuario", nullable = false, unique = true, length = 10) 
    private String usuario;

    @Column(name = "contraseña", nullable = false) // Establecido como no nulo
    private String contrasena;

    @NotBlank(message = "El nombre completo no puede estar vacio.")
    @Size(max = 30, message = "El nombre completo no puede exceder 30 caracteres.")
    @Column(name = "nombreCompleto", nullable = false, length = 30) 
    private String nombreCompleto;

    @NotBlank(message = "El email no puede estar vacio.")
    @Email(message = "El formato del email no es valido.")
    @Column(name="email", nullable = false, unique = true, length = 20) 
    private String email;

    @Column(name="estado", nullable = false) // Establecido como no nulo
    private Boolean estado;

    /// mapeo a tabla roles
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rol_id", nullable = false) // Se asume que un usuario siempre debe tener un rol
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

    public Usuarios(Integer usuario_id, List<DevolucionCompra> devolucionCompra, List<Devolucion> devoluciones, List<Venta> ventas, Set<ControlMovimientos> controlMovimientosSet, Roles roles, Boolean estado, String nombreCompleto, String email, String contrasena, String usuario) {
        this.usuario_id = usuario_id;
        this.devolucionCompra = devolucionCompra;
        this.devoluciones = devoluciones;
        this.ventas = ventas;
        this.controlMovimientosSet = controlMovimientosSet;
        this.roles = roles;
        this.estado = estado;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.contrasena = contrasena;
        this.usuario = usuario;
    }

            public Usuarios(Integer usuarioId) {
    }

    // --- Getters y Setters ---

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

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuarios usuarios = (Usuarios) o;
        return Objects.equals(usuario_id, usuarios.usuario_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario_id);
    }
}