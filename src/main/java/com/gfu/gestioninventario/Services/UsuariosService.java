package com.gfu.gestioninventario.Services;

import com.gfu.gestioninventario.Models.Usuarios;
import com.gfu.gestioninventario.Repository.UsuariosRepository;
import com.gfu.gestioninventario.Models.Roles;
import com.gfu.gestioninventario.Models.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private RolesService rolesService;

    //@Autowired
    //private PasswordEncoder passwordEncoder; 

    public List<Usuarios> findAllUsuarios() {
        return usuariosRepository.findAll();
    }

    public Optional<Usuarios> findById(Integer id) {
        return usuariosRepository.findById(id);
    }

    public Usuarios saveUsuario(Usuarios usuario) {
        Usuarios usuarioToSave = usuario;

        if (usuarioToSave.getUsuario_id() == null) {
            // Logica para CREAR un nuevo usuario
            usuarioToSave.setEstado(true);

            // Validar que la contrasena sea obligatoria y cumpla con la longitud minima para nuevos usuarios
            if (usuarioToSave.getContrasena() == null || usuarioToSave.getContrasena().trim().isEmpty()) {
                throw new IllegalArgumentException("La contrasena es obligatoria para nuevos usuarios.");
            }
            if (usuarioToSave.getContrasena().length() < 8) {
                throw new IllegalArgumentException("La contrasena debe tener al menos 8 caracteres.");
            }
            // Encriptar la contrasena antes de guardar para nuevos usuarios
            //usuarioToSave.setContrasena(passwordEncoder.encode(usuarioToSave.getContrasena()));

        } else {
            // Logica para EDITAR un usuario existente
            Optional<Usuarios> existingUserOptional = usuariosRepository.findById(usuarioToSave.getUsuario_id());
            if (existingUserOptional.isEmpty()) {
                throw new IllegalArgumentException("Usuario a editar no encontrado.");
            }
            Usuarios existingUser = existingUserOptional.get();

            if (usuarioToSave.getEstado() == null) {
                usuarioToSave.setEstado(false);
            }

            // Manejar el campo 'contrasena'
            // Si el campo de contrasena se dejo en blanco en la edicion, mantenemos la contrasena antigua
            if (usuarioToSave.getContrasena() == null || usuarioToSave.getContrasena().trim().isEmpty()) {
                 usuarioToSave.setContrasena(existingUser.getContrasena()); // Conservar la contrasena existente sin encriptar
            } else {
                // Si el campo de contrasena se lleno, significa que el usuario quiere cambiarla.
                // Validamos que la nueva contrasena cumpla con la longitud minima.
                if (usuarioToSave.getContrasena().length() < 8) {
                    throw new IllegalArgumentException("La nueva contrasena debe tener al menos 8 caracteres.");
                }
                // Encriptar la NUEVA contrasena antes de guardar
                //usuarioToSave.setContrasena(passwordEncoder.encode(usuarioToSave.getContrasena()));
            }

            // Asegurarse de que el rol del usuario se mantenga
            if (usuarioToSave.getRoles() == null || usuarioToSave.getRoles().getRole_id() == null ||
                !usuarioToSave.getRoles().getRole_id().equals(existingUser.getRoles().getRole_id())) {
                usuarioToSave.setRoles(existingUser.getRoles());
            }
        }

        // Validaciones de unicidad para nombre de usuario, email y nombreCompleto
        validateUsuarioUniqueness(usuarioToSave);

        try {
            return usuariosRepository.save(usuarioToSave);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage() != null && (e.getMessage().toLowerCase().contains("duplicate key") || e.getMessage().toLowerCase().contains("unique constraint"))) {
                throw new RuntimeException("Ya existe un usuario con datos duplicados (posiblemente nombre de usuario, email o nombre completo). Por favor, verifique los datos.", e);
            }
            throw new RuntimeException("Ocurrio un error al guardar el usuario: " + e.getMessage(), e);
        }
    }

    private void validateUsuarioUniqueness(Usuarios usuario) {
        Optional<Usuarios> existingUsuarioByUsername = usuariosRepository.findByUsuario(usuario.getUsuario());
        if (existingUsuarioByUsername.isPresent() && !existingUsuarioByUsername.get().getUsuario_id().equals(usuario.getUsuario_id())) {
            throw new IllegalArgumentException("El nombre de usuario '" + usuario.getUsuario() + "' ya existe. Por favor, ingrese otro.");
        }

        Optional<Usuarios> existingUsuarioByEmail = usuariosRepository.findByEmail(usuario.getEmail());
        if (existingUsuarioByEmail.isPresent() && !existingUsuarioByEmail.get().getUsuario_id().equals(usuario.getUsuario_id())) {
            throw new IllegalArgumentException("El email '" + usuario.getEmail() + "' ya existe. Por favor, ingrese otro.");
        }

        Optional<Usuarios> existingUsuarioByNombreCompleto = usuariosRepository.findByNombreCompleto(usuario.getNombreCompleto());
        if (existingUsuarioByNombreCompleto.isPresent() && !existingUsuarioByNombreCompleto.get().getUsuario_id().equals(usuario.getUsuario_id())) {
            throw new IllegalArgumentException("El nombre completo '" + usuario.getNombreCompleto() + "' ya existe. Por favor, ingrese otro.");
        }
    }

    public void deleteById(Integer id) {
        usuariosRepository.deleteById(id);
    }

    // Metodo para buscar usuarios
    public List<Usuarios> searchUsuarios(String keyword) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            // Llama al metodo de busqueda del repositorio
            return usuariosRepository.searchByKeyword(keyword);
        }
        // Si el keyword es nulo o vacio, retorna todos los usuarios
        return findAllUsuarios();
    }

    public void asignarRolAUsuario(Usuarios usuario, RoleType tipoRol) {
        Roles rol = rolesService.findByTipo_rol(tipoRol)
                                 .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + tipoRol));
        usuario.setRoles(rol);
    }
}