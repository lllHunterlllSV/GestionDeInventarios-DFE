package com.gfu.gestioninventario.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "proveedores")
public class Proveedores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="proveedor_id")
    private Integer proveedorId;

    @NotBlank(message = "El nombre del proveedor no puede estar vacío")
    @Size(max = 100, message = "El nombre del proveedor no debe exceder los 100 caracteres")
    @Column(name = "nombreProveedor", nullable = false, length = 100)
    private String nombreProveedor;

    @Size(max = 25, message = "El NCR/NIT no debe exceder los 25 caracteres")
    @Column(name="NCR_NIT", length = 25)
    private String ncrNit;

    @NotNull(message = "El teléfono no puede ser nulo")
    @Column(nullable = false)
    private Integer telefono;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe ser válido")
    @Size(max = 100, message = "El email no debe exceder los 100 caracteres")
    @Column(nullable = false, length = 100)
    private String email;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(max = 150, message = "La dirección no debe exceder los 150 caracteres")
    @Column(nullable = false, length = 150)
    private String direccion;

    @Size(max = 100, message = "La persona no debe exceder los 100 caracteres")
    @Column(length = 100)
    private String persona;

    @OneToMany(mappedBy = "proveedor")
    private Set<OrdenCompra> OrdenCompra;
    @OneToMany (mappedBy = "proveedores")
    private Set<Producto> Productos;


    // Getters y setters

    public Integer getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Integer proveedorId) {
        this.proveedorId = proveedorId;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getNcrNit() {
        return ncrNit;
    }

    public void setNcrNit(String ncrNit) {
        this.ncrNit = ncrNit;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public Set<OrdenCompra> getOrdenCompra() {
        return OrdenCompra;
    }

    public void setOrdenCompra(Set<OrdenCompra> ordenCompra) {
        OrdenCompra = ordenCompra;
    }

    public Set<Producto> getProductos() {
        return Productos;
    }

    public void setProductos(Set<Producto> productos) {
        Productos = productos;
    }

    // Constructor sin parámetros
    public Proveedores() {
    }

    // Constructor con parámetros


    public Proveedores(Integer proveedorId, Set<Producto> productos, Set<com.gfu.gestioninventario.Models.OrdenCompra> ordenCompra, String persona, String direccion, String email, Integer telefono, String ncrNit, String nombreProveedor) {
        this.proveedorId = proveedorId;
        Productos = productos;
        OrdenCompra = ordenCompra;
        this.persona = persona;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.ncrNit = ncrNit;
        this.nombreProveedor = nombreProveedor;
    }
}
