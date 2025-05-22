package com.gfu.gestioninventario.Services;

import com.gfu.gestioninventario.Models.Categoria;
import com.gfu.gestioninventario.Models.Producto;
import com.gfu.gestioninventario.Repository.ProductoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService implements ProductoServiceInt{

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    EntityManager entityManager;

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

    public Page<Producto> buscarProductosPorCategoria(
            int categoriaId,
            String keyword,
            Pageable pageable,
            String sortField,
            String sortDir) {

        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();

        // Validar campos para evitar SQL injection
        List<String> camposOrdenables = List.of("producto_id", "nombre", "descripcion", "precio", "stock");
        sortField = camposOrdenables.contains(sortField) ? sortField : "producto_id";
        sortDir = sortDir.equalsIgnoreCase("desc") ? "desc" : "asc";

        // Llamar al stored procedure con ordenamiento dinámico
        Query query = entityManager.createNativeQuery(
                "EXEC sp_buscar_productos_por_categoria_y_keyword " +
                        ":categoriaId, :keyword, :page, :size, :sortField, :sortDir",
                Producto.class
        );

        query.setParameter("categoriaId", categoriaId);
        query.setParameter("keyword", keyword);
        query.setParameter("page", page);
        query.setParameter("size", size);
        query.setParameter("sortField", sortField);
        query.setParameter("sortDir", sortDir);

        @SuppressWarnings("unchecked")
        List<Producto> productos = query.getResultList();

        // Total (si no lo integraste al SP)
        Query countQuery = entityManager.createNativeQuery(
                "SELECT COUNT(*) FROM dbo.productos WHERE categoria_id = ?1 " +
                        "AND (LOWER(nombre) LIKE CONCAT('%', LOWER(?2), '%') " +
                        "OR LOWER(descripcion) LIKE CONCAT('%', LOWER(?2), '%'))"
        );
        countQuery.setParameter(1, categoriaId);
        countQuery.setParameter(2, keyword);
        long total = ((Number) countQuery.getSingleResult()).longValue();

        return new PageImpl<>(productos, pageable, total);
    }





}