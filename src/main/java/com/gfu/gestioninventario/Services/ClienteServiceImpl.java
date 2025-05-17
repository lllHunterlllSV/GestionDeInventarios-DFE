package com.gfu.gestioninventario.Services;

import com.gfu.gestioninventario.Models.Cliente;
import com.gfu.gestioninventario.Models.TipoCliente;
import com.gfu.gestioninventario.Repository.ClienteRepository;
import com.gfu.gestioninventario.Repository.TipoClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final TipoClienteRepository tipoClienteRepository;
    

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository, TipoClienteRepository tipoClienteRepository) {
        this.clienteRepository = clienteRepository;
        this.tipoClienteRepository = tipoClienteRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findById(Integer id) {
        return clienteRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findByNIF(String nif) {
        return clienteRepository.findByNIF(nif);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findByDUI(String dui) {
        return clienteRepository.findByDUI(dui);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findByTelefono(Integer telefono) {
        return clienteRepository.findByTelefono(telefono);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findByTipoCliente(TipoCliente tipoCliente) {
        return clienteRepository.findByTipoCliente(tipoCliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findByTipoClienteId(Integer tipoClienteId) {
        Optional<TipoCliente> tipoCliente = tipoClienteRepository.findById(tipoClienteId);
        return tipoCliente.map(clienteRepository::findByTipoCliente).orElse(List.of());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findByNombreContaining(String nombre) {
        return clienteRepository.findByNombreClienteContainingIgnoreCase(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAllActiveClients() {
        return clienteRepository.findAllActiveClients();
    }

    @Override
    @Transactional
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        clienteRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return clienteRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByNIF(String nif) {
        return clienteRepository.existsByNIF(nif);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByDUI(String dui) {
        return clienteRepository.existsByDUI(dui);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return clienteRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> buscarClientes(String nombre, Integer tipoClienteId, Boolean estado) {
        return clienteRepository.buscarClientes(nombre, tipoClienteId, estado);
    }

    @Override
    @Transactional
    public Cliente activarCliente(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
        cliente.setEstado(true);
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional
    public Cliente desactivarCliente(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
        cliente.setEstado(false);
        return clienteRepository.save(cliente);
    }
}