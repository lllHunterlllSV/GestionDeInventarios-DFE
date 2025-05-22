package com.gfu.gestioninventario.Controller;

import com.gfu.gestioninventario.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    private final ProductoRepository productoRepository;
    private final LoteRepository loteRepository;
    private final ClienteRepository clienteRepository;
    private final ProveedoresRepository proveedorRepository;
    // @Autowired
    // private final VentaRepository ventaRepository;
    private final OrdenCompraRepository ordenCompraRepository;

    @Autowired
    public DashboardController(
        ProductoRepository productoRepository,
        LoteRepository loteRepository,
        ClienteRepository clienteRepository,
        ProveedoresRepository proveedorRepository,
        // VentaRepository ventaRepository,
        OrdenCompraRepository ordenCompraRepository
    ) {
        this.productoRepository = productoRepository;
        this.loteRepository = loteRepository;
        this.clienteRepository = clienteRepository;
        this.proveedorRepository = proveedorRepository;
        // this.ventaRepository = ventaRepository;
        this.ordenCompraRepository = ordenCompraRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        long totalProductos = productoRepository.count();
        long totalLotes = loteRepository.count();
        long totalClientes = clienteRepository.count();
        long totalProveedores = proveedorRepository.count();

        long totalVentas = 0; // Por ahora en cero si ventaRepository está comentado
        long totalOrdenesCompra = ordenCompraRepository.count();

        List<Object[]> productosPorCategoria = productoRepository.countProductsByCategory();

        model.addAttribute("totalProductos", totalProductos);
        model.addAttribute("totalLotes", totalLotes);
        model.addAttribute("totalClientes", totalClientes);
        model.addAttribute("totalProveedores", totalProveedores);
        model.addAttribute("totalVentas", totalVentas);
        model.addAttribute("totalOrdenesCompra", totalOrdenesCompra);
        model.addAttribute("productosPorCategoria", productosPorCategoria);
        model.addAttribute("title", "Dashboard");

        return "dashboard";
    }
}
