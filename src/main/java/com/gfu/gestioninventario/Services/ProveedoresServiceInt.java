package com.gfu.gestioninventario.Services;

import com.gfu.gestioninventario.Models.Proveedores;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProveedoresServiceInt {
    Page<Proveedores> findAll(Pageable pageable);

}
