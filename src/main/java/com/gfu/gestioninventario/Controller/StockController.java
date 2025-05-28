package com.gfu.gestioninventario.Controller;

import com.gfu.gestioninventario.DTOs.StockDTO;
import com.gfu.gestioninventario.Services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/stock")
public class StockController {
    @Autowired
    private StockService stockService;

    // Define size options as a constant
    private static final List<Integer> SIZE_OPTIONS = List.of(5, 10, 20, 50);
    private static final int LOW_STOCK_THRESHOLD = 10;

    @GetMapping("/lista")
    public String listarStock(Model model,
                              @RequestParam(defaultValue = "") String keyword,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "id") String sortField,
                              @RequestParam(defaultValue = "asc") String sortDir) {

        // Validate page number
        if (page < 0) {
            page = 0;
        }

        // Validate size
        if (!SIZE_OPTIONS.contains(size)) {
            size = 10; // default size
        }

        StockService.StockResult resultado = stockService.buscarStockPorKeyword(keyword, page, size);

        // Add all required attributes to the model
        model.addAttribute("stocks", resultado.getStocks());
        model.addAttribute("totalRecords", resultado.getTotalRecords());
        model.addAttribute("keyword", keyword);
        model.addAttribute("size", size);
        model.addAttribute("page", page); // Using "page" instead of "paginaActual" for consistency
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("sizeOptions", SIZE_OPTIONS);

        model.addAttribute("lowStockThreshold", LOW_STOCK_THRESHOLD);

        // Calculate total pages
        int totalPages = (int) Math.ceil((double) resultado.getTotalRecords() / size);
        model.addAttribute("totalPages", totalPages);

        return "mostrarStock";
    }
}
