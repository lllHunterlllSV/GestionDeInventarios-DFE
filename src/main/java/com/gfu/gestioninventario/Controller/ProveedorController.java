package com.gfu.gestioninventario.Controller;

import com.gfu.gestioninventario.Models.Proveedores;
import com.gfu.gestioninventario.Repository.ProveedoresRepository;
import com.gfu.gestioninventario.Services.ProveedoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/proveedores")
public class ProveedorController {
    @Autowired
    private ProveedoresRepository proveedoresRepository;
    @Autowired
    private ProveedoresService proveedoresService;

    @GetMapping("/lista")
    public String listarProveedores(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "proveedorId") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Proveedores> paginaProveedores = keyword.isEmpty() ?
                proveedoresService.findAll(pageable) :
                proveedoresService.buscarPorKeyword(keyword, pageable);

        model.addAttribute("proveedores", paginaProveedores);
        model.addAttribute("keyword", keyword);
        model.addAttribute("size", size);
        model.addAttribute("paginaActual", page);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("sizeOptions", List.of(5, 10, 20, 50));

        return "gestionProveedores";
    }

    @PostMapping("/guardarProveedor")
    public String guardarProveedores(@ModelAttribute Proveedores proveedores, Model model) {
        try {
            if (proveedores.getProveedorId() == null) {
                if (proveedoresService.existeProveedor(proveedores.getNcrNit(), proveedores.getPersona(), proveedores.getEmail(), proveedores.getTelefono(), proveedores.getNombreProveedor())) {
                    model.addAttribute("error", "El proveedor ya existe");
                    return "formularioProveedor"; // Misma vista de formulario
                }
                proveedoresService.ingresarNuevoProveedores(proveedores);
                model.addAttribute("exito", "El formulario fue guardado con exito");
            } else  {
                // Tiene ID → modificar proveedor existente
                proveedoresService.modificarProveedor(proveedores);
                model.addAttribute("exito", "El formulario fue editado con exito");
            }

            return "formularioProveedor"; // Misma vista de formulario
        } catch(Exception e){
            model.addAttribute("error", "Error al guardar: " + e.getMessage());
            return "formularioProveedor";
        }


    }
    @GetMapping("/form")
        public String form(Model model) {
        model.addAttribute("proveedores", new Proveedores());
        return "formularioProveedor";
    }
    @GetMapping("/editar/{id}")
    public String editarProveedor(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Proveedores> proveedorOpt = proveedoresService.BuscarporID(id);
        if (proveedorOpt.isPresent()) {
            model.addAttribute("proveedores", proveedorOpt.get());
            return "formularioProveedor"; // usa la misma vista del formulario
        } else {
            // Manejo en caso de no encontrarlo
            return "redirect:/proveedores/lista";
        }
    }
    @PostMapping("/eliminar/{id}")
    public String eliminarProveedor(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            proveedoresService.eliminarProveedor(id);
            redirectAttributes.addFlashAttribute("exito", "Proveedor eliminado con éxito");
            return "redirect:/proveedores/lista";
        } catch (RuntimeException ex) {
            // Vuelve a cargar la lista y muestra el error directamente en la vista
            List<Proveedores> lista = proveedoresService.listaeProveedores();
            model.addAttribute("listaProveedores", lista);
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el proveedor");
            return "listaProveedores"; // Asegúrate de usar el nombre correcto de tu plantilla
        }
    }






}