package com.gfu.gestioninventario.Models;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name="roles")


public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="rol_id")
    private Integer role_id;
    @Enumerated(EnumType.STRING)
    private RoleType tipo_rol;


    @OneToMany(mappedBy = "roles")
    private Set<Usuarios> usuarios;



    /// constructor
    public Roles(Integer role_id, RoleType tipo_rol) {
        this.role_id = role_id;
        this.tipo_rol = tipo_rol;
    }
    public Roles() {

    }

    /// Getters y setters
    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public RoleType getTipo_rol() {
        return tipo_rol;
    }

    public void setTipo_rol(RoleType tipo_rol) {
        this.tipo_rol = tipo_rol;
    }
}
