package com.gfu.gestioninventario.Repository;

import com.gfu.gestioninventario.Models.Roles;
import com.gfu.gestioninventario.Models.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; 
import org.springframework.data.repository.query.Param; 
import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Integer> {
    @Query("SELECT r FROM Roles r WHERE r.tipo_rol = :tipo")
    Optional<Roles> findByTipo_rol(@Param("tipo") RoleType tipoRol);
}
