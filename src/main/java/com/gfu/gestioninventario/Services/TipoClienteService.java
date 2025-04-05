package com.gfu.gestioninventario.Services;

import com.gfu.gestioninventario.Models.TipoCliente;
import java.util.List;
import java.util.Optional;

public interface TipoClienteService {
    List<TipoCliente> findAll();
    Optional<TipoCliente> findById(Integer id);
    Optional<TipoCliente> findByTipoCliente(String tipoCliente);
    TipoCliente save(TipoCliente tipoCliente);
    void deleteById(Integer id);
    boolean existsById(Integer id);
    boolean existsByTipoCliente(String tipoCliente);
}