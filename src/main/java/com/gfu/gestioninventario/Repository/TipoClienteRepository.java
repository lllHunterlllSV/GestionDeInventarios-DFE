package com.gfu.gestioninventario.Repository;

import com.gfu.gestioninventario.Models.TipoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TipoClienteRepository extends JpaRepository<TipoCliente, Integer> {
    Optional<TipoCliente> findByTipoCliente(String tipoCliente);
    boolean existsByTipoCliente(String tipoCliente);
}