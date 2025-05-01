package com.gfu.gestioninventario.Services;

import com.gfu.gestioninventario.Models.EstadoOrden;
import com.gfu.gestioninventario.Models.Proveedores;
import com.gfu.gestioninventario.Repository.OrdenCompraRepository;
import com.gfu.gestioninventario.Repository.ProveedoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedoresService {
    @Autowired
    private ProveedoresRepository proveedoresRepository;
    @Autowired
    private OrdenCompraRepository ordenCompraRepository;


    //listar proveedores
    public List<Proveedores> listaeProveedores() {
        return proveedoresRepository.findAll();
    }

    //buscar proveedores por ID
    public Optional<Proveedores> BuscarporID(Integer id) {
        return proveedoresRepository.findById(id);
    }


    public void ingresarNuevoProveedores(Proveedores proveedor) {


        if (proveedor.getProveedorId() != null && proveedoresRepository.existsById(proveedor.getProveedorId())) {
            throw new RuntimeException("El proveedor ya existe");
        }

        proveedoresRepository.save(proveedor);

    }

    public void modificarProveedor(Proveedores proveedor) {
        proveedoresRepository.save(proveedor);
    }

    public void eliminarProveedor(Integer proveedorId) {
        if (proveedorId == null || !proveedoresRepository.existsById(proveedorId)) {
            throw new RuntimeException("El proveedor no existe");
        }
        /*

        if (ordenCompraRepository.existsByIdAndEstado(proveedorId, EstadoOrden.PENDIENTE)) {
            throw new RuntimeException("No se puede eliminar el proveedor con órdenes pendientes");
        }
        */

        if(ordenCompraRepository.existsByProveedor_ProveedorIdAndEstado(proveedorId,EstadoOrden.PENDIENTE)){
            throw new RuntimeException("No se puede eliminar el proveedor con órdenes pendientes");

        }

        proveedoresRepository.deleteById(proveedorId);
    }




}
