package com.gfu.gestioninventario.Repository;

import com.gfu.gestioninventario.Models.EstadoOrden;
import com.gfu.gestioninventario.Models.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoteRepository extends JpaRepository<Lote, Integer> {
    List<Lote> findByOrdenOrdenId(Integer ordenId);
    Optional<Lote> findByOrdenOrdenIdAndProductoProductoId(Integer ordenId, Integer productoId);

    @Modifying
    @Query("DELETE FROM Lote l WHERE l.orden.ordenId = :ordenId")
    void deleteByOrdenId(@Param("ordenId") Integer ordenId);

    @Modifying
    @Query("DELETE FROM ControlMovimientos c WHERE c.lote.loteId = :loteId")
    void deleteByLoteId(@Param("loteId") Integer loteId);

    List<Lote> findByProducto_ProductoIdAndCantidadGreaterThanAndOrden_EstadoNot(
            Integer productoId,
            int cantidad,
            EstadoOrden estado
    );


}
