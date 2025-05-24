package com.gfu.gestioninventario.Repository;

import com.gfu.gestioninventario.Models.Categoria;
import com.gfu.gestioninventario.Models.Producto;
import java.util.List;

import com.gfu.gestioninventario.Models.Proveedores;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @Query("SELECT p FROM Producto p WHERE p.proveedores.proveedorId = :proveedorId")
    List<Producto> findByProveedorProveedorId(@Param("proveedorId") Integer proveedorId);


    // Busquedas flexibles
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
   //List<Producto> findByCategoriaContainingIgnoreCase(String categoria);
   boolean existsByNombreIgnoreCase(String nombre);

    Page<Producto> findByNombreContainingIgnoreCase(String keyword, Pageable pageable);

    boolean existsByCategoria_Id(int categoria);


    @Query("SELECT p FROM Producto p WHERE p.categoria.id = :categoriaId")
    Page<Producto> findByCategoriaId(@Param("categoriaId") int categoriaId, Pageable pageable);

    // Consulta con búsqueda (versión optimizada)
    @Query("SELECT p FROM Producto p WHERE p.categoria.id = :categoriaId AND " +
            "(LOWER(p.nombre) LIKE %:keyword% OR LOWER(p.descripcion) LIKE %:keyword%)")
    Page<Producto> findByCategoriaIdAndKeyword(
            @Param("categoriaId") int categoriaId,
            @Param("keyword") String keyword,
            Pageable pageable);
}
