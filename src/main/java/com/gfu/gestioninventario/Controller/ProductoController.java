package com.gfu.gestioninventario.Controller;

import com.gfu.gestioninventario.Models.Categoria;
import com.gfu.gestioninventario.Models.Producto;
import com.gfu.gestioninventario.Models.Proveedores;
import com.gfu.gestioninventario.Repository.ProductoRepository;
import com.gfu.gestioninventario.Services.CategoriaService;
import com.gfu.gestioninventario.Services.MedidasService;
import com.gfu.gestioninventario.Services.ProductoService;
import com.gfu.gestioninventario.Services.ProveedoresService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/productos")
public class ProductoController {


    @Autowired
    private ProductoService productoService;
    @Autowired
    private ProveedoresService proveedoresService;
    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private MedidasService medidasService;
    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/form")

    public String formulario(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("proveedores", proveedoresService.listadeProveedores());
        model.addAttribute("medidas", medidasService.listaMedidas());
        model.addAttribute("categorias",categoriaService.obtenerTodasCategorias());
        return "formularioProducto";
    }

    @PostMapping("/guardarProducto")
    public String guardarProducto(@ModelAttribute Producto producto,
                                  @RequestParam(required = false) String nuevaCategoria, RedirectAttributes redirectAttributes) {
        if (nuevaCategoria != null && !nuevaCategoria.trim().isEmpty()) {
            Categoria categoriaExistente = categoriaService.obtenerCategorias(nuevaCategoria.trim());

            if (categoriaExistente == null) {
                Categoria nueva = new Categoria();
                nueva.setNombre(nuevaCategoria.trim());
                categoriaExistente = categoriaService.guardarCategoria(nueva);
            }

            producto.setCategoria(categoriaExistente);
        }
        if(producto.getProductoId()==null){
            if(productoService.buscarProductoPorNombre(producto.getNombre())){
                redirectAttributes.addFlashAttribute("error", "El producto ya existe");
                return "redirect:/productos/form";
            }
            productoService.crearProducto(producto);
            return "redirect:/productos/form";
        }else{
            productoService.actualizarProducto(producto);
            return "redirect:/productos/productosCat/" + producto.getCategoria().getId();
        }





    }

    /// Categorias controller

    @GetMapping("/categorias")
    public String filtrarCategorias(
            @RequestParam(required = false) String keyword,
            Model model) {

        // Obtener todas las categorías si no hay keyword, o filtrar si hay
        List<Categoria> categorias = (keyword == null || keyword.isEmpty())
                ? categoriaService.obtenerTodasCategorias()
                : categoriaService.buscarCategoriasPorKeyword(keyword);

        model.addAttribute("categorias", categorias);
        model.addAttribute("keyword", keyword); // Para mantener el valor en el input

        return "productosCategorias";
    }

    //mostrar categorias
    @GetMapping("/productosCat/{id}")
    public String productosCat(
            @PathVariable int id,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "productoId") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {

        // Validación y normalización de parámetros
        page = Math.max(page, 0);
        size = size <= 0 ? 5 : size;
        sortField = Arrays.asList("productoId", "nombre", "precio", "stock").contains(sortField) ?
                sortField : "productoId";
        sortDir = sortDir.equalsIgnoreCase("desc") ? "desc" : "asc";

        // Configuración de paginación
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        // Obtener categoría con manejo de errores
        Categoria categoria = categoriaService.obtenerPorIdCategoria(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada con ID: " + id));

        // Búsqueda de productos
        Page<Producto> paginaProductos = productoService.buscarProductosPorCategoria(
                id,
                keyword.trim(),
                pageable,
                sortField,
                sortDir
        );




        // Configuración del modelo
        model.addAttribute("categoria", categoria);
        model.addAttribute("productos", paginaProductos);
        model.addAttribute("keyword", keyword);
        model.addAttribute("paginaActual", page);
        model.addAttribute("size", size);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("sizeOptions", List.of(5, 10, 20, 50));

        return "gestionProductos";
    }
    //agregar categoria
    @PostMapping("/agregarCategoria")
    public String agregarCategoria(@ModelAttribute Categoria categoria, RedirectAttributes redirectAttributes) {
        try {
            if(categoriaService.obtenerCategorias(categoria.getNombre()) != null) {
                redirectAttributes.addFlashAttribute("error", "a existe una categoria con ese nombre");
            }
            categoriaService.guardarCategoria(categoria);
            redirectAttributes.addFlashAttribute("exito", "Categoria agregada con exito");
            return "redirect:/productos/categorias";

        }catch (Exception e) {
            String mensajeError = e.getMessage();

            if (mensajeError != null && mensajeError.contains("Query did not return a unique result")) {
                redirectAttributes.addFlashAttribute("error", "Ya existe una categoría con ese nombre.");
            } else {
                redirectAttributes.addFlashAttribute("error", "No se puede agregar la categoría: " + mensajeError);
            }
        }
        return "redirect:/productos/categorias";
    }




    //eliminar categoria

    @PostMapping("/eliminarCategoria/{id}")
    public String eliminarCategoria(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            categoriaService.eliminarCategoria(id);
            redirectAttributes.addFlashAttribute("exito", "Categoría eliminada con éxito");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se puede eliminar la categoría: " + e.getMessage());
        }

        return "redirect:/productos/categorias";
    }


    //editar producto
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model) {
        Optional<Producto> productoEditado = productoService.obtenerProductoPorId(id);
        if (productoEditado.isPresent()) {
            model.addAttribute("producto", productoEditado.get());
            model.addAttribute("proveedores", proveedoresService.listadeProveedores());
            model.addAttribute("medidas", medidasService.listaMedidas());
            model.addAttribute("categorias",categoriaService.obtenerTodasCategorias());
            return "formularioProducto";
        }else {
            return "redirect:/productos/productosCat/{id}";


        }

    }
    //eliminar producto

    @PostMapping("/eliminar/{id}")
    String eliminarProducto(@PathVariable int id,  RedirectAttributes redirectAttributes) {

        try {
            Producto producto = productoService.obtenerProductoPorId(id).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
            int idCategoria = producto.getCategoria().getId();
            redirectAttributes.addFlashAttribute("exito", "Producto eliminado correctamente");

            productoService.eliminarProducto(id);

            return "redirect:/productos/productosCat/"+idCategoria;
        }catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
            return "redirect:/productos/categorias";

        }
    }






}
