package com.gfu.gestioninventario.Services;
import com.gfu.gestioninventario.Models.Usuarios;
import com.gfu.gestioninventario.Repository.UsuariosRepository;
import com.gfu.gestioninventario.security.CustomUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Usuarios usuario = usuariosRepository.findByUsuario(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    return new CustomUserDetails(usuario);
    }
    
}
