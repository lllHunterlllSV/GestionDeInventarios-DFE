package com.gfu.gestioninventario.Services;

import com.gfu.gestioninventario.Models.EstadoOrden;
import com.gfu.gestioninventario.Models.Proveedores;
import com.gfu.gestioninventario.Repository.OrdenCompraRepository;
import com.gfu.gestioninventario.Repository.ProveedoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedoresService implements ProveedoresServiceInt{
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

    public boolean existeProveedor(String ncrNit,String persona, String email,
                                   Integer telefono, String nombreProveedor) {
        return proveedoresRepository.ExisteProveedores(ncrNit,persona,email,telefono,nombreProveedor);

    }

    public void modificarProveedor(Proveedores proveedor) {
        proveedoresRepository.save(proveedor);
    }

    public void eliminarProveedor(Integer proveedorId) {
        if (proveedorId == null || !proveedoresRepository.existsById(proveedorId)) {
            throw new RuntimeException("El proveedor no existe");
        }



        if(ordenCompraRepository.existsByProveedor_ProveedorIdAndEstado(proveedorId,EstadoOrden.PENDIENTE)){
            throw new RuntimeException("No se puede eliminar el proveedor con órdenes pendientes");

        }

        proveedoresRepository.deleteById(proveedorId);
    }


    @Override
    public Page<Proveedores> findAll(Pageable pageable) {
        return proveedoresRepository.findAll(pageable);
    }

    @Override
    public Page<Proveedores> buscarPorKeyword(String keyword, Pageable pageable) {
        return proveedoresRepository.buscarPorKeyword(keyword, pageable);
    }
}
