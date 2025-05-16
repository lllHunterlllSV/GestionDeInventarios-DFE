package com.gfu.gestioninventario.Repository;

import com.gfu.gestioninventario.Models.Categoria;
import com.gfu.gestioninventario.Models.Producto;
import java.util.List;

import com.gfu.gestioninventario.Models.Proveedores;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    // Busquedas flexibles
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
   //List<Producto> findByCategoriaContainingIgnoreCase(String categoria);
   boolean existsByNombreIgnoreCase(String nombre);

    Page<Producto> findByNombreContainingIgnoreCase(String keyword, Pageable pageable);

    boolean existsByCategoria_Id(int categoria);





}
