package com.gfu.gestioninventario.Repository;

import com.gfu.gestioninventario.Models.Venta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {

    @Query("""
        SELECT v FROM Venta v 
        WHERE LOWER(v.cliente.nombreCliente) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(v.numeroFactura) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR CONCAT('', v.ventaId) LIKE CONCAT('%', :keyword, '%')
    """)
    Page<Venta> buscarPorKeyword(@Param("keyword") String keyword, Pageable pageable);
}

