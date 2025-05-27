package com.gfu.gestioninventario.Repository;

import com.gfu.gestioninventario.Models.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoteRepository extends JpaRepository<Lote, Integer> {
    List<Lote> findByOrdenOrdenId(Integer ordenId);
    Optional<Lote> findByOrdenOrdenIdAndProductoProductoId(Integer ordenId, Integer productoId);



}
