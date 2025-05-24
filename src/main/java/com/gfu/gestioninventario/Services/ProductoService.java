package com.gfu.gestioninventario.Services;

import com.gfu.gestioninventario.Models.Producto;
import com.gfu.gestioninventario.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService implements ProductoServiceInt{

    @Autowired
    private ProductoRepository productoRepository;

    // CREAR
    public Producto crearProducto(Producto producto) {
        // Validación básica
        if(producto.getNombre() == null || producto.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es requerido");
        }
        if(productoRepository.existsByNombreIgnoreCase(producto.getNombre())) {
            throw new IllegalArgumentException("El nombre del producto ya existe");
        }
        return productoRepository.save(producto);
    }

    public boolean buscarProductoPorNombre(String nombre) {
        return productoRepository.existsByNombreIgnoreCase(nombre);
    }

    // LISTAR
    public List<Producto> obtenerTodosProductos() {
        return productoRepository.findAll();
    }

    // LISTAR POR ID
    public Optional<Producto> obtenerProductoPorId(Integer id) {
        return productoRepository.findById(id);
    }

    // ACTUALIZAR
    public Producto actualizarProducto(Producto producto) {
        if(!productoRepository.existsById(producto.getProductoId())) {
            throw new RuntimeException("Producto no encontrado con ID: " + producto.getProductoId());
        }
        return productoRepository.save(producto);
    }

    // ELIMINAR
    public void eliminarProducto(Integer id) {
        if(!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }
        productoRepository.deleteById(id);
    }

    // Búsqueda por nombre (adicional)
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }



    @Override
    public Page<Producto> findAll(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }

    @Override
    public Page<Producto> buscarPorKeyword(String keyword, Pageable pageable) {
        return productoRepository.findByNombreContainingIgnoreCase(keyword, pageable);
    }
    public Page<Producto> buscarProductosPorCategoria(int categoriaId, String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return productoRepository.findByCategoriaId(categoriaId, pageable);
        }
        return productoRepository.findByCategoriaIdAndKeyword(categoriaId, keyword.toLowerCase(), pageable);
    }

    public List<Producto> findByProveedorId(Integer proveedorId) {
        return productoRepository.findByProveedorProveedorId(proveedorId);
    }

}