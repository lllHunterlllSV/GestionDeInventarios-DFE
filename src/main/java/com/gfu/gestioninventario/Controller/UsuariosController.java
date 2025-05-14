package com.gfu.gestioninventario.Controller;

import com.gfu.gestioninventario.Models.RoleType;
import com.gfu.gestioninventario.Models.Usuarios;
import com.gfu.gestioninventario.Services.UsuariosService;
import com.gfu.gestioninventario.Services.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import java.util.Optional;
import java.util.List;

@Controller
@RequestMapping("/configuracion/usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosService usuariosService;

    @Autowired
    private RolesService rolesService;

    @GetMapping
    public String configuracionUsuarios(Model model) {
        model.addAttribute("title", "Configuracion de Usuarios");
        return "usuariosConfiguracion";
    }

    @GetMapping("/lista")
    // Anadimos @RequestParam para aceptar un parametro 'keyword' opcional
    public String listarUsuarios(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Usuarios> usuarios;
        if (keyword != null && !keyword.trim().isEmpty()) {
            // Si hay un keyword, buscamos por el
            usuarios = usuariosService.searchUsuarios(keyword);
        } else {
            // Si no hay keyword, listamos todos los usuarios
            usuarios = usuariosService.findAllUsuarios();
        }
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("title", "Listado de Usuarios");
        model.addAttribute("keyword", keyword); // Enviamos el keyword de vuelta al HTML para mantenerlo en la barra de busqueda
        return "gestionUsuarios";
    }

    @GetMapping("/nuevo/{rolTipo}")
    public String formularioNuevoUsuario(@PathVariable String rolTipo, Model model, RedirectAttributes redirectAttributes) {
        RoleType roleTypeEnum;
        try {
            roleTypeEnum = RoleType.valueOf(rolTipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Rol de usuario no valido.");
            return "redirect:/configuracion/usuarios";
        }

        Usuarios nuevoUsuario = new Usuarios();
        usuariosService.asignarRolAUsuario(nuevoUsuario, roleTypeEnum);

        model.addAttribute("usuario", nuevoUsuario);
        model.addAttribute("title", "Nuevo Usuario " + rolTipo.toUpperCase());
        model.addAttribute("isEdit", false);
        return "formularioUsuario";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditarUsuario(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Usuarios> usuarioExistente = usuariosService.findById(id);

        if (usuarioExistente.isPresent()) {
            Usuarios usuario = usuarioExistente.get();
            usuario.setContrasena(null);

            model.addAttribute("usuario", usuario);
            model.addAttribute("title", "Editar Usuario");
            model.addAttribute("isEdit", true);
            return "formularioUsuario";
        } else {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado para edicion.");
            return "redirect:/configuracion/usuarios/lista";
        }
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@Valid @ModelAttribute Usuarios usuario, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        // Validacion automatica de anotaciones @Valid del modelo
        if (bindingResult.hasErrors()) {
            model.addAttribute("usuario", usuario); // Mantiene los datos del formulario
            model.addAttribute("title", usuario.getUsuario_id() == null ? "Nuevo Usuario" : "Editar Usuario");
            model.addAttribute("isEdit", usuario.getUsuario_id() != null);

            if (usuario.getUsuario_id() != null) {
                usuariosService.findById(usuario.getUsuario_id()).ifPresent(u -> {
                    if (usuario.getRoles() == null || !u.getRoles().equals(u.getRoles())) {
                        usuario.setRoles(u.getRoles());
                    }
                });
            }
            return "formularioUsuario";
        }

        // Logica de negocio y validaciones adicionales en el servicio
        try {
            usuariosService.saveUsuario(usuario);
            redirectAttributes.addFlashAttribute("exito", "Usuario guardado correctamente.");
            return "redirect:/configuracion/usuarios/lista";
        } catch (IllegalArgumentException e) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("error", e.getMessage()); // Mensaje de error del servicio

            model.addAttribute("title", usuario.getUsuario_id() == null ? "Nuevo Usuario" : "Editar Usuario");
            model.addAttribute("isEdit", usuario.getUsuario_id() != null);

            if (usuario.getUsuario_id() != null) {
                usuariosService.findById(usuario.getUsuario_id()).ifPresent(u -> {
                    if (usuario.getRoles() == null || !u.getRoles().equals(u.getRoles())) {
                        usuario.setRoles(u.getRoles());
                    }
                });
            }
            return "formularioUsuario";
        } catch (RuntimeException e) {
            // Otros errores inesperados del servicio
            model.addAttribute("usuario", usuario);
            model.addAttribute("error", "Ocurrio un error inesperado: " + e.getMessage());

            model.addAttribute("title", usuario.getUsuario_id() == null ? "Nuevo Usuario" : "Editar Usuario");
            model.addAttribute("isEdit", usuario.getUsuario_id() != null);

            if (usuario.getUsuario_id() != null) {
                usuariosService.findById(usuario.getUsuario_id()).ifPresent(u -> {
                    if (usuario.getRoles() == null || !u.getRoles().equals(u.getRoles())) {
                        usuario.setRoles(u.getRoles());
                    }
                });
            }
            return "formularioUsuario";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            usuariosService.deleteById(id);
            redirectAttributes.addFlashAttribute("exito", "Usuario eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el usuario: " + e.getMessage());
        }
        return "redirect:/configuracion/usuarios/lista";
    }
}