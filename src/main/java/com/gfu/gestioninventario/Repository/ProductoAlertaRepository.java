package com.gfu.gestioninventario.Repository;

import com.gfu.gestioninventario.DTOs.AlertaStockDTO;
import com.gfu.gestioninventario.Models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoAlertaRepository extends JpaRepository<Producto, Integer> {
    @Query(value = "SELECT producto_id, producto, stock_total, categoria FROM vista_stock_bajo", nativeQuery = true)
    List<Object[]> findVistaStockBajo();


}
