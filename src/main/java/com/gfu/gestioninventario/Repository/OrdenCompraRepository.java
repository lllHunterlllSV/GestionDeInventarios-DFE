package com.gfu.gestioninventario.Repository;

import com.gfu.gestioninventario.Models.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Integer> {
    //verificar si hay ordenes activas de un proveedor
    boolean existsByOrdenIdAndEstado(Integer ordenId, Enum estado);;
    boolean existsByProveedor_ProveedorIdAndEstado(Integer proveedorId, Enum estado);

}
