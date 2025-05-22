package com.gfu.gestioninventario.Services;

import com.gfu.gestioninventario.Models.Categoria;
import com.gfu.gestioninventario.Repository.CategoriaRepository;
import com.gfu.gestioninventario.Repository.ProductoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @PersistenceContext
    EntityManager entityManager;

    public Categoria obtenerCategorias(String categoria) {
        return categoriaRepository.findByNombreIgnoreCase(categoria);
    }

    public Categoria guardarCategoria(Categoria categoria) {
        if(categoriaRepository.findByNombreIgnoreCase(categoria.getNombre()) != null) {
            throw new IllegalArgumentException("El categoria ya existe");
        }
        return categoriaRepository.save(categoria);
    }
    public List<Categoria> obtenerTodasCategorias() {
        return categoriaRepository.findAll();
    }
    public Optional<Categoria> obtenerPorIdCategoria(int idCategoria) {
       return categoriaRepository.findById(idCategoria);
    }
    public void eliminarCategoria(int idCategoria) {

        Categoria cat = categoriaRepository.findById(idCategoria).orElseThrow(()-> new RuntimeException("No se encontro el categoria"));
        if(productoRepository.existsByCategoria_Id(idCategoria)) {
            throw new RuntimeException("No se puede eliminar categorias con productos");
        }
        categoriaRepository.delete(cat);

    }
    /// buscar por categoria
    public List<Categoria> buscarCategoriasPorKeyword(String keyword) {
        return categoriaRepository.buscarPorKeyword(keyword);
    }
}
