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
    // @Autowired // Comentado temporalmente hasta que se suba VentaRepository
    // private VentaRepository ventaRepository;
    private final OrdenCompraRepository ordenCompraRepository; 


    @Autowired
    public DashboardController(
        ProductoRepository productoRepository,
        LoteRepository loteRepository,
        ClienteRepository clienteRepository,
        ProveedoresRepository proveedorRepository,
        // VentaRepository ventaRepository, // Comentado
        OrdenCompraRepository ordenCompraRepository // Inyectado
    ) {
        this.productoRepository = productoRepository;
        this.loteRepository = loteRepository;
        this.clienteRepository = clienteRepository;
        this.proveedorRepository = proveedorRepository;
        // this.ventaRepository = ventaRepository; // Comentado
        this.ordenCompraRepository = ordenCompraRepository; // Asignado
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        // Obtiene los conteos de las entidades relevantes
        long totalProductos = productoRepository.count();
        long totalLotes = loteRepository.count();
        long totalClientes = clienteRepository.count();
        long totalProveedores = proveedorRepository.count();

        long totalVentas = 0; // Valor por defecto si VentaRepository esta comentado
        long totalOrdenesCompra = ordenCompraRepository.count(); // Conteo total de ordenes de compra


        // Obtiene los datos para la grafica de productos por categoria
        List<Object[]> productosPorCategoria = productoRepository.countProductsByCategory();


        // Anade los datos al modelo para pasarlos a la vista
        model.addAttribute("totalProductos", totalProductos);
        model.addAttribute("totalLotes", totalLotes);
        model.addAttribute("totalClientes", totalClientes);
        model.addAttribute("totalProveedores", totalProveedores);
        model.addAttribute("totalVentas", totalVentas);
        model.addAttribute("totalOrdenesCompra", totalOrdenesCompra); 
        model.addAttribute("productosPorCategoria", productosPorCategoria); // Datos para la grafica de productos


        model.addAttribute("title", "Dashboard"); // Titulo para la pagina
        return "dashboard"; // Nombre del archivo HTML de la vista del dashboard
    }
}
