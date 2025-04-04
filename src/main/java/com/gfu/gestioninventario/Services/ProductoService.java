package com.gfu.gestioninventario.Services;

import com.gfu.gestioninventario.Models.Producto;
import com.gfu.gestioninventario.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // CREAR
    public Producto crearProducto(Producto producto) {
        // Validación básica
        if(producto.getNombre() == null || producto.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es requerido");
        }
        return productoRepository.save(producto);
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
}