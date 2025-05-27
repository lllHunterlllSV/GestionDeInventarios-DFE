package com.gfu.gestioninventario.Repository;

import com.gfu.gestioninventario.Models.ControlMovimientos;
import com.gfu.gestioninventario.Models.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ControlMovimientosRepository extends JpaRepository<ControlMovimientos, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM ControlMovimientos c WHERE c.lote.loteId = :loteId")
    void deleteByLoteId(@Param("loteId") Integer loteId);

    List<ControlMovimientos> findByLoteAndTipoMovimiento_Id(Lote lote, Integer tipoMovimientoId);
    void deleteByLoteAndTipoMovimiento_Id(Lote lote, Integer tipoMovimientoId);

}
