package com.gfu.gestioninventario.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.gfu.gestioninventario.Models.Usuarios;
import java.util.List;


public interface UsuariosRepository extends JpaRepository<Usuarios, Integer>{
    Optional<Usuarios> findByUsuario(String usuario);
    Optional<Usuarios> findByEmail(String email);
    Optional<Usuarios> findByNombreCompleto(String nombreCompleto);

    // Nuevo metodo para buscar usuarios por palabra clave en multiples campos
    // Busca por ID (convertido a String), usuario, nombreCompleto, email o rol
    @Query("SELECT u FROM Usuarios u WHERE " +
           "CAST(u.usuario_id AS string) LIKE CONCAT('%', :keyword, '%') OR " + // Buscar por ID como String
           "LOWER(u.usuario) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(u.nombreCompleto) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(u.roles.tipo_rol) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Usuarios> searchByKeyword(@Param("keyword") String keyword);
}
