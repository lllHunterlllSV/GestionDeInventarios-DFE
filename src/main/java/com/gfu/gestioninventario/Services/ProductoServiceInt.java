package com.gfu.gestioninventario.Services;

import com.gfu.gestioninventario.Models.Producto;
import com.gfu.gestioninventario.Models.Proveedores;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductoServiceInt {
    Page<Producto> findAll(Pageable pageable);
    Page<Producto> buscarPorKeyword(String keyword, Pageable pageable);
}
