package com.gfu.gestioninventario;

import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gfu.gestioninventario.Models.Usuarios;
import com.gfu.gestioninventario.Repository.UsuariosRepository;

@SpringBootApplication
public class TestLogin implements CommandLineRunner{


    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        
    }

    @Override
    public void run(String... args) throws Exception{
        Scanner scanner = new Scanner(System.in);

        System.out.println("Usuario");
        String username = scanner.nextLine();

        System.out.println("Contrase;a");
        String password = scanner.nextLine();

        Optional<Usuarios> userOpt = usuariosRepository.findByUsuario(username);

        if (userOpt.isPresent()) {
            Usuarios user = userOpt.get();

            if (user.getEstado() ) {
                
            }
        }


    }


}
