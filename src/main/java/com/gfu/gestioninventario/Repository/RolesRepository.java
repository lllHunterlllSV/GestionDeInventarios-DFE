package com.gfu.gestioninventario.Repository;

import com.gfu.gestioninventario.Models.Roles;
import com.gfu.gestioninventario.Models.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; 
import org.springframework.data.repository.query.Param; 
import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Integer> {
    // Esto ignora cualquier intento de Spring Data JPA de "parsear" el nombre del metodo.
    @Query("SELECT r FROM Roles r WHERE r.tipo_rol = :tipo")
    Optional<Roles> findByTipo_rol(@Param("tipo") RoleType tipoRol); // El parametro 'tipoRol' se mapea a ':tipo' en la query.
}