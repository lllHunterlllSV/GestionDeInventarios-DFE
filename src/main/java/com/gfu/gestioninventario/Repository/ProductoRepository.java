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

    // Busquedas flexibles
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    boolean existsByNombreIgnoreCase(String nombre);
    Page<Producto> findByNombreContainingIgnoreCase(String keyword, Pageable pageable);
    boolean existsByCategoria_Id(int categoria);

    // Consulta con búsqueda (versión optimizada)
    @Query("SELECT p FROM Producto p WHERE p.categoria.id = :categoriaId AND " +
           "(LOWER(p.nombre) LIKE %:keyword% OR LOWER(p.descripcion) LIKE %:keyword%)")
    Page<Producto> findByCategoriaIdAndKeyword(
            @Param("categoriaId") int categoriaId,
            @Param("keyword") String keyword,
            Pageable pageable);

    // Método para obtener la cantidad de productos por categoría
    @Query("SELECT p.categoria.nombre, COUNT(p) FROM Producto p GROUP BY p.categoria.nombre")
    List<Object[]> countProductsByCategory();
}
