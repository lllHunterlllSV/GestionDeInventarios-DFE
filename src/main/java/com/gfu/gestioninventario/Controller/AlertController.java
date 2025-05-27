package com.gfu.gestioninventario.Controller;

import com.gfu.gestioninventario.DTOs.AlertaStockDTO;
import com.gfu.gestioninventario.Repository.ProductoAlertaRepository;
import com.gfu.gestioninventario.Services.AlertaStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/alerta")
public class AlertController {
    @Autowired
    private AlertaStockService alertaStockService;

    @GetMapping("/stock-bajo")
    public String mostrarAlertaStockBajo(Model model) {
        List<AlertaStockDTO> productos = alertaStockService.obtenerProductosConStockBajo();
        model.addAttribute("productos", productos);
        return "announcement/stock-bajo"; // tu vista
    }
}
