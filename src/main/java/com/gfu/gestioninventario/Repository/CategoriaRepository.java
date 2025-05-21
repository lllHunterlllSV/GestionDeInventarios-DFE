package com.gfu.gestioninventario.Repository;

import com.gfu.gestioninventario.Models.Categoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

   Categoria findByNombreIgnoreCase(String nombre);


   @Query("SELECT c FROM Categoria c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :keyword, '%'))")
   List<Categoria> buscarPorKeyword(@Param("keyword") String keyword);



}
