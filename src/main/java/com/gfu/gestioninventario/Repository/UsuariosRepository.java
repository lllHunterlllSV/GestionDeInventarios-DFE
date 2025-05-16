package com.gfu.gestioninventario.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gfu.gestioninventario.Models.Usuarios;


public interface UsuariosRepository extends JpaRepository<Usuarios, Integer>{
    Optional<Usuarios> findByUsuario(String usuario);

}
