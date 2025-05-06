package com.gfu.gestioninventario.Repository;

import com.gfu.gestioninventario.Models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

   Categoria findByNombreIgnoreCase(String nombre);






}
