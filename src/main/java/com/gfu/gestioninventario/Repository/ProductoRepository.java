package com.gfu.gestioninventario.Repository;

import com.gfu.gestioninventario.Models.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    // Busquedas flexibles
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

}
