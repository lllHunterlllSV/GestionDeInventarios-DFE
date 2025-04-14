package com.gfu.gestioninventario.Services;

import com.gfu.gestioninventario.Models.Cliente;
import com.gfu.gestioninventario.Models.TipoCliente;
import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<Cliente> findAll();
    Optional<Cliente> findById(Integer id);
    Optional<Cliente> findByNIF(String nif);
    Optional<Cliente> findByDUI(String dui);
    Optional<Cliente> findByEmail(String email);
    Optional<Cliente> findByTelefono(Integer telefono);
    List<Cliente> findByTipoCliente(TipoCliente tipoCliente);
    List<Cliente> findByTipoClienteId(Integer tipoClienteId);
    List<Cliente> findByNombreContaining(String nombre);
    List<Cliente> findAllActiveClients();
    Cliente save(Cliente cliente);
    void deleteById(Integer id);
    boolean existsById(Integer id);
    boolean existsByNIF(String nif);
    boolean existsByDUI(String dui);
    boolean existsByEmail(String email);
    List<Cliente> buscarClientes(String nombre, Integer tipoClienteId, Boolean estado);
    Cliente activarCliente(Integer id);
    Cliente desactivarCliente(Integer id);
}