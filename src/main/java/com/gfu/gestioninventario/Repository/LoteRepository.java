package com.gfu.gestioninventario.Repository;

import com.gfu.gestioninventario.Models.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoteRepository extends JpaRepository<Lote, Integer> {
    List<Lote> findByProductoProductoId(Integer productoId);
    List<Lote> findByEstadoTrue();
    //boolean existsByProductoProductoIdAndOrdenCompraOrdenId(Integer productoId, Integer ordenId);
    boolean existsByProducto_ProductoIdAndOrden_OrdenId(Integer productoId, Integer ordenId);

}