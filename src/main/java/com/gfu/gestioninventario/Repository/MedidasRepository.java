package com.gfu.gestioninventario.Repository;

import com.gfu.gestioninventario.Models.Medidas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedidasRepository extends JpaRepository<Medidas, Integer> {

    List<Medidas> findAll();

}
