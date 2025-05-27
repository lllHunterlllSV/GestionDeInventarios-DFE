package com.gfu.gestioninventario.Services;

import com.gfu.gestioninventario.DTOs.AlertaStockDTO;
import com.gfu.gestioninventario.Repository.ProductoAlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlertaStockService {
    @Autowired
    private ProductoAlertaRepository productoAlertaRepository;

    public List<AlertaStockDTO> obtenerProductosConStockBajo() {
        List<Object[]> datos = productoAlertaRepository.findVistaStockBajo();
        List<AlertaStockDTO> dtos = new ArrayList<>();

        for (Object[] fila : datos) {
            Integer productoId = ((Number) fila[0]).intValue();
            String producto = (String) fila[1];
            Long stockTotal = fila[2] != null ? ((Number) fila[2]).longValue() : 0L;
            String categoria = (String) fila[3];

            dtos.add(new AlertaStockDTO(productoId, producto, stockTotal, categoria));
        }

        return dtos;
    }
}
