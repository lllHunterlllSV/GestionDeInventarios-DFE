package com.gfu.gestioninventario.Services;

import com.gfu.gestioninventario.Models.Medidas;
import com.gfu.gestioninventario.Repository.MedidasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedidasService {
    @Autowired
    private MedidasRepository medidasRepository;
    public List<Medidas> listaMedidas(){
        return medidasRepository.findAll();
    }
}
