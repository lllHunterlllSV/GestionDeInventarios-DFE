package com.gfu.gestioninventario.Repository;

import com.gfu.gestioninventario.Models.Proveedores;
import jakarta.validation.constraints.Size;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface ProveedoresRepository extends JpaRepository<Proveedores, Integer> {

    /// existe proveedor
    boolean existsById(int id);

    /// Busca proveedores por id
    Proveedores findById(int id);

    Proveedores findByNcrNit(String ncrNit);


    ////procedimiento almacenado
    ///
    /**
    @Query("""
    SELECT p FROM Proveedores p 
    WHERE LOWER(p.nombreProveedor) LIKE LOWER(CONCAT('%', :keyword, '%')) 
    OR LOWER(p.ncrNit) LIKE LOWER(CONCAT('%', :keyword, '%')) 
    OR LOWER(p.direccion) LIKE LOWER(CONCAT('%', :keyword, '%'))
    OR LOWER(p.persona) LIKE LOWER(CONCAT('%', :keyword, '%'))
    OR LOWER(p.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
    OR CONCAT('', p.telefono) LIKE CONCAT('%', :keyword, '%')  
    OR CONCAT('', p.proveedorId) LIKE CONCAT('%', :keyword, '%')
""")
    Page<Proveedores> buscarPorKeyword(@Param("keyword") String keyword, Pageable pageable);
    */




}
