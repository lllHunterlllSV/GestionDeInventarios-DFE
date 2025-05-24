package com.gfu.gestioninventario.Controller;
/*
import com.gfu.gestioninventario.Models.Usuarios;
import com.gfu.gestioninventario.Services.UsuariosService;
import com.gfu.gestioninventario.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class PerfilUsuarioController {
    @Autowired
    private UsuariosService usuariosService;


    @GetMapping("/perfil")
    public String perfil(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        Usuarios usuario = userDetails.getUsuario();
        model.addAttribute("usuario", usuario);

        return"/loginUsuarios/perfilUsuario";
    }
    @PostMapping("/editar")
    public String editar(Model model, Usuarios usuario) {
        Usuarios usuarioEdit = usuariosService.findById(usuario.getUsuario_id()).orElseThrow();
        usuarioEdit.setNombreCompleto(usuario.getNombreCompleto());
        usuarioEdit.setEmail(usuario.getEmail());


        return"/loginUsuarios/perfilUsuario";

    }

}*/
