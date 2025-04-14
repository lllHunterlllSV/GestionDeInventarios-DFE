package com.gfu.gestioninventario.Repository;

import com.gfu.gestioninventario.Models.Proveedores;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedoresRepository extends JpaRepository<Proveedores, Integer> {

    /// existe proveedor
    boolean existsById(int id);

    /// Busca proveedores por id
    Proveedores findById(int id);
    /// Buscar por NIT
    Proveedores findbyNCR(String ncr);





}
