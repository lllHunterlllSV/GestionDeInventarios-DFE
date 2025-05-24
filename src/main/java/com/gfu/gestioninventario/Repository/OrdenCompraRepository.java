package com.gfu.gestioninventario.Repository;

import com.gfu.gestioninventario.Models.OrdenCompra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Integer> {
    List<OrdenCompra> findByProveedorProveedorId(Integer proveedorId);
    boolean existsByOrdenIdAndEstado(Integer ordenId, Enum estado);
    boolean existsByProveedor_ProveedorIdAndEstado(Integer proveedorId, Enum estado);


        @Query("""
    SELECT o FROM OrdenCompra o 
    WHERE LOWER(o.proveedor.nombreProveedor) LIKE LOWER(CONCAT('%', :keyword, '%'))
    OR LOWER(o.estado) LIKE LOWER(CONCAT('%', :keyword, '%'))
    OR CONCAT('', o.ordenId) LIKE CONCAT('%', :keyword, '%')
    """)
        Page<OrdenCompra> buscarPorKeyword(@Param("keyword") String keyword, Pageable pageable);
    }

