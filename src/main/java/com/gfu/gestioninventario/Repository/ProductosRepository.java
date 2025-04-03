package com.gfu.gestioninventario.Repository;

import com.gfu.gestioninventario.Models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductosRepository extends JpaRepository<Producto, Integer> {
}
