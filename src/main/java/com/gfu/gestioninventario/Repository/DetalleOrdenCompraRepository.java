
package com.gfu.gestioninventario.Repository;

import com.gfu.gestioninventario.Models.DetalleOrdenCompra;
import com.gfu.gestioninventario.Models.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleOrdenCompraRepository extends JpaRepository<DetalleOrdenCompra, Integer> {
    List<DetalleOrdenCompra> findByOrdenOrdenId(Integer ordenId);
}