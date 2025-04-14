package com.gfu.gestioninventario.Services;

import com.gfu.gestioninventario.Models.TipoCliente;
import com.gfu.gestioninventario.Repository.TipoClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TipoClienteServiceImpl implements TipoClienteService {

    private final TipoClienteRepository tipoClienteRepository;

    @Autowired
    public TipoClienteServiceImpl(TipoClienteRepository tipoClienteRepository) {
        this.tipoClienteRepository = tipoClienteRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoCliente> findAll() {
        return tipoClienteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoCliente> findById(Integer id) {
        return tipoClienteRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoCliente> findByTipoCliente(String tipoCliente) {
        return tipoClienteRepository.findByTipoCliente(tipoCliente);
    }

    @Override
    @Transactional
    public TipoCliente save(TipoCliente tipoCliente) {
        return tipoClienteRepository.save(tipoCliente);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        tipoClienteRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return tipoClienteRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByTipoCliente(String tipoCliente) {
        return tipoClienteRepository.existsByTipoCliente(tipoCliente);
    }
}