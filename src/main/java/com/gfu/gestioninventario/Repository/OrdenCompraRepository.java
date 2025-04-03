package com.gfu.gestioninventario.Repository;

import com.gfu.gestioninventario.Models.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Integer> {
    //verificar si hay ordenes activas de un proveedor
    boolean existsByIdAndEstado(Integer idEstado, Enum estado);
}
