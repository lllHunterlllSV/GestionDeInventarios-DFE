package com.gfu.gestioninventario.Services;

import com.gfu.gestioninventario.Models.EstadoOrden;
import com.gfu.gestioninventario.Models.Proveedores;
import com.gfu.gestioninventario.Repository.OrdenCompraRepository;
import com.gfu.gestioninventario.Repository.ProveedoresRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
    @Autowired
    EntityManager entityManager;


    //listar proveedores
    public List<Proveedores> listadeProveedores() {
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


    public boolean existeProveedor(String ncrNit, String persona, String email, int telefono, String nombreProveedor) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_verificar_existencia_proveedor");

        query.registerStoredProcedureParameter("p_ncr_nit", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_persona", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_email", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_telefono", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_nombre_proveedor", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_existe", Boolean.class, ParameterMode.OUT);

        query.setParameter("p_ncr_nit", ncrNit);
        query.setParameter("p_persona", persona);
        query.setParameter("p_email", email);
        query.setParameter("p_telefono", telefono);
        query.setParameter("p_nombre_proveedor", nombreProveedor);

        query.execute();

        return (Boolean) query.getOutputParameterValue("p_existe");
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

    public Page<Proveedores> buscarPorKeyword(String keyword, int page, int size) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_buscar_proveedores_por_keyword", Proveedores.class);

        // Registrar parámetros
        query.registerStoredProcedureParameter("p_keyword", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_page_number", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_page_size", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_total_records", Long.class, ParameterMode.OUT);

        // Establecer valores
        query.setParameter("p_keyword", keyword);
        query.setParameter("p_page_number", page);
        query.setParameter("p_page_size", size);

        // Ejecutar
        List<Proveedores> resultList = query.getResultList();
        Long total = (Long) query.getOutputParameterValue("p_total_records");

        return new PageImpl<>(resultList, PageRequest.of(page - 1, size), total);
    }
}
