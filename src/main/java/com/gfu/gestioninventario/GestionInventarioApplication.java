package com.gfu.gestioninventario;

import com.gfu.gestioninventario.Models.RoleType;
import com.gfu.gestioninventario.Models.Roles;
import com.gfu.gestioninventario.Models.Usuarios;
import com.gfu.gestioninventario.Repository.RolesRepository;
import com.gfu.gestioninventario.Repository.UsuariosRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class
GestionInventarioApplication {
    /*
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UsuariosRepository usuariosRepository;
    @Autowired
    RolesRepository rolesRepository;



    @PostConstruct
    public void nuevoUsuario() {
        Usuarios usuario = new Usuarios();
        Roles role = new Roles();
        role.setTipo_rol(RoleType.ADMIN);
        rolesRepository.save(role);

        usuario.setUsuario("user");
        usuario.setContrasena(passwordEncoder.encode("123"));
        usuario.setEmail("user@gmail.com");
        usuario.setEstado(true);
        usuario.setNombreCompleto("Pepe Aguilar");
        usuario.setRoles(role);
        usuariosRepository.save(usuario);

    }*/

    public static void main(String[] args) {
        SpringApplication.run(GestionInventarioApplication.class, args);


    }


}
