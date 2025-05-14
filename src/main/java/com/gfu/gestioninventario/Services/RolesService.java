package com.gfu.gestioninventario.Services;

import com.gfu.gestioninventario.Models.Roles;
import com.gfu.gestioninventario.Models.RoleType;
import com.gfu.gestioninventario.Repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RolesService {

    @Autowired
    private RolesRepository rolesRepository;

    public List<Roles> findAllRoles() {
        return rolesRepository.findAll();
    }

    public Optional<Roles> findById(Integer id) {
        return rolesRepository.findById(id);
    }

    public Optional<Roles> findByTipo_rol(RoleType tipo_rol) {
        return rolesRepository.findByTipo_rol(tipo_rol);
    }

    //si es necesario, aqui se pueden agregar mas metodos
    
}
