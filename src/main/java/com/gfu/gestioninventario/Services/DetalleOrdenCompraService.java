package com.gfu.gestioninventario.Services;

import com.gfu.gestioninventario.Models.DetalleOrdenCompra;
import com.gfu.gestioninventario.Repository.DetalleOrdenCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleOrdenCompraService {
    @Autowired
    private DetalleOrdenCompraRepository detalleOrdenCompraRepository;

    public DetalleOrdenCompra agregarProducto(DetalleOrdenCompra detalle) {
        return detalleOrdenCompraRepository.save(detalle);
    }

    public List<DetalleOrdenCompra> obtenerPorOrden(Integer ordenId) {
        return detalleOrdenCompraRepository.findByOrdenOrdenId(ordenId);
    }
}
