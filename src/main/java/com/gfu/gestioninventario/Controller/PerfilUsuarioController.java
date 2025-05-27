package com.gfu.gestioninventario.Controller;

import com.gfu.gestioninventario.Models.Usuarios;
import com.gfu.gestioninventario.Services.UsuariosService;
import com.gfu.gestioninventario.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class PerfilUsuarioController {
    @Autowired
    private UsuariosService usuariosService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/perfil")
    public String perfil(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        Usuarios usuario = userDetails.getUsuario();
        model.addAttribute("usuario", usuario);

        return"/loginUsuarios/perfilUsuario";
    }
    @PostMapping("/editar")
    public String editar(@ModelAttribute Usuarios usuario, BindingResult result, Model model, @AuthenticationPrincipal UserDetails userDetails) {

        Optional<Usuarios> usuarios = usuariosService.findByUsername(userDetails.getUsername());
        if (usuarios.isPresent()) {
            Usuarios usuarioEdit=usuarios.get();


            usuarioEdit.setNombreCompleto(usuario.getNombreCompleto());
            usuarioEdit.setEmail(usuario.getEmail());
            usuarioEdit.setNombreCompleto(usuario.getNombreCompleto());
            if (usuarioEdit.getContrasena()!=null  && !usuarioEdit.getContrasena().isEmpty()) {
                if(usuarioEdit.getContrasena().equals(usuario.getContrasena())) {
                    model.addAttribute("error", "No se puede ingresar la contrasena anteriormente");
                }
                usuarioEdit.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
            }else {
                model.addAttribute("error", "No se puede ingresar contrasena vacia");
            }
            usuariosService.save(usuarioEdit);
            model.addAttribute("success", "Datos actualizados correctamente.");
        }
        else {
            model.addAttribute("error", "Usuario no encontrado.");
        }



        return"redirect:/usuario/perfil";

    }

}
