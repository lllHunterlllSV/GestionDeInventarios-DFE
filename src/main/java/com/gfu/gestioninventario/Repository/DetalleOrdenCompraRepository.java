
package com.gfu.gestioninventario.Repository;

import com.gfu.gestioninventario.Models.DetalleOrdenCompra;
import com.gfu.gestioninventario.Models.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DetalleOrdenCompraRepository extends JpaRepository<DetalleOrdenCompra, Integer> {
    List<DetalleOrdenCompra> findByOrden(OrdenCompra orden);
}