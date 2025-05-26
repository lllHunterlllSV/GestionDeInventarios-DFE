package com.gfu.gestioninventario.Services;

import com.gfu.gestioninventario.Models.*;
import com.gfu.gestioninventario.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OrdenCompraService {
    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @Autowired
    private DetalleOrdenCompraRepository detalleOrdenCompraRepository;

    @Autowired
    private ProveedoresRepository proveedorRepository;

    @Autowired
    private LoteRepository loteRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public OrdenCompra crearOrden(OrdenCompra orden) {
        orden.setEstado(EstadoOrden.PENDIENTE);
        return ordenCompraRepository.save(orden);
    }

    public void eliminarOrdenCompleta(Integer ordenId) {
        try {
            jdbcTemplate.update("EXEC sp_EliminarOrdenCompraCompleta ?", ordenId);
        } catch (Exception e) {
            // Puedes registrar aquí si lo deseas
            throw new RuntimeException("No se pudo eliminar la orden. Motivo: " + e.getMessage(), e);
        }
    }





    public List<OrdenCompra> listarPorProveedor(Integer proveedorId) {
        return ordenCompraRepository.findByProveedorProveedorId(proveedorId);
    }

    public OrdenCompra obtenerPorId(Integer id) {
        return ordenCompraRepository.findById(id).orElseThrow();
    }

    public OrdenCompra actualizarOrden(OrdenCompra orden) {
        return ordenCompraRepository.save(orden);
    }

    public void eliminarOrden(Integer ordenId) {
        ordenCompraRepository.deleteById(ordenId);
    }
    public Page<OrdenCompra> listarOrdenes(Pageable pageable) {
        return ordenCompraRepository.findAll(pageable);
    }

    public Page<OrdenCompra> buscarPorKeyword(String keyword, Pageable pageable) {
        return ordenCompraRepository.buscarPorKeyword(keyword, pageable);
    }





}