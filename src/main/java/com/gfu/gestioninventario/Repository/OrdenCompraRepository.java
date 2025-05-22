package com.gfu.gestioninventario.Repository;

import com.gfu.gestioninventario.Models.EstadoOrden;
import com.gfu.gestioninventario.Models.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List; 


public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Integer> {
    //verificar si hay ordenes activas de un proveedor
    boolean existsByOrdenIdAndEstado(Integer ordenId, Enum estado);
    boolean existsByProveedor_ProveedorIdAndEstado(Integer proveedorId, Enum estado);


}
