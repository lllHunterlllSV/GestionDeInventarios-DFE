package com.gfu.gestioninventario.Controller;

import com.gfu.gestioninventario.DTOs.VentaCompletaDTO;
import com.gfu.gestioninventario.Models.EstadoOrden;
import com.gfu.gestioninventario.Models.Venta;
import com.gfu.gestioninventario.Repository.ClienteRepository;
import com.gfu.gestioninventario.Repository.LoteRepository;
import com.gfu.gestioninventario.Repository.ProductoRepository;
import com.gfu.gestioninventario.Services.ClienteService;
import com.gfu.gestioninventario.Services.VentaService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.gfu.gestioninventario.Models.Lote;


import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private ClienteService clienteService; // Para futuras vistas/modales
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private ProductoRepository productoRepository;
    @Autowired private LoteRepository loteRepository;
    @Autowired
    private DataSource dataSource;

    @GetMapping("/lista")
    public String listarVentas(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "ventaId") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {

        try {
            Sort sort = sortDir.equalsIgnoreCase("asc")
                    ? Sort.by(sortField).ascending()
                    : Sort.by(sortField).descending();

            Pageable pageable = PageRequest.of(page, size, sort);

            Page<Venta> paginaVentas = keyword.isEmpty()
                    ? ventaService.listarVentas(pageable)
                    : ventaService.buscarPorKeyword(keyword, pageable);

            System.out.println("✔ Total ventas: " + paginaVentas.getTotalElements());
            for (Venta v : paginaVentas.getContent()) {
                System.out.println("→ Venta ID: " + v.getVentaId()
                        + " | Cliente: " + (v.getCliente() != null ? v.getCliente().getNombreCliente() : "NULO")
                        + " | Fecha: " + v.getFechaVenta()
                        + " | Total: " + v.getTotal()
                        + " | Notas: " + v.getNotas());
            }

            model.addAttribute("ventas", paginaVentas);
            model.addAttribute("keyword", keyword);
            model.addAttribute("paginaActual", page);
            model.addAttribute("size", size);
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDir", sortDir);
            model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
            model.addAttribute("sizeOptions", List.of(5, 10, 20, 50));

            return "ventas/listado_ventas";

        } catch (Exception e) {
            System.err.println("❌ ERROR CRÍTICO al cargar ventas:");
            e.printStackTrace(System.err); // ← Muestra el error exacto en consola
            return "error"; // vista genérica o temporal
        }
    }






    @PostMapping("/ventaCompleta")
    public String procesarVentaCompleta(@ModelAttribute VentaCompletaDTO form) {
        try {
            ventaService.registrarVentaCompleta(
                    form.getClienteId(),
                    form.getFechaVenta(),
                    form.getDetalles(),
                    form.getUsuarioId(),
                    form.getTipoMovimientoId()
            );
            return "redirect:/ventas/lista?exito=La venta fue registrada correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/ventas/lista?error=Ocurrió un error al registrar la venta";
        }
    }

    @GetMapping("/api/clientes")
    @ResponseBody
    public List<Map<String, Object>> getClientes() {
        return clienteRepository.findByEstadoTrue().stream().map(c -> {
            Map<String, Object> map = new HashMap<>();
            map.put("clienteId", c.getClienteId());
            map.put("nombreCliente", c.getNombreCliente());
            return map;
        }).collect(Collectors.toList());
    }

    // 🔹 GET: Lista de productos
    @GetMapping("/api/productos")
    @ResponseBody
    public List<Map<String, Object>> getProductos() {
        return productoRepository.findAll().stream().map(p -> {
            Map<String, Object> map = new HashMap<>();
            map.put("productoId", p.getProductoId());
            map.put("nombre", p.getNombre());
            return map;
        }).collect(Collectors.toList());
    }

    @GetMapping("/api/lotes-disponibles/{productoId}")
    @ResponseBody
    public List<Map<String, Object>> getLotesDisponibles(@PathVariable Integer productoId) {
        return loteRepository
                .findByProducto_ProductoIdAndCantidadGreaterThanAndOrden_EstadoNot(
                        productoId, 0, EstadoOrden.PENDIENTE
                )
                .stream()
                .filter(Lote::getEstado) // solo lotes activos
                .map(l -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("loteId", l.getLoteId());
                    map.put("cantidadDisponible", l.getCantidad());
                    map.put("fechaIngreso", l.getFechaIngreso());
                    map.put("fechaVencimiento", l.getFechaVencimiento());
                    map.put("precioVenta", l.getProducto().getPrecioVenta());
                    return map;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/factura/{ventaId}")
    public void generarFactura(@PathVariable Integer ventaId, HttpServletResponse response) {
        Connection conn = null;
        OutputStream outStream = null;
        InputStream inputStream = null;

        try {
            // 1. Obtener la conexión a la base de datos
            conn = dataSource.getConnection();

            // 2. Obtener el archivo .jasper desde /resources/reportes
            ClassPathResource resource = new ClassPathResource("reportes/Factura_venta.jasper");

            if (!resource.exists()) {
                throw new FileNotFoundException("❌ El archivo Factura_venta.jasper no fue encontrado en /resources/reportes/");
            }

            inputStream = resource.getInputStream();


            JasperReport reporte = (JasperReport) JRLoader.loadObject(inputStream);

            // 4. Parámetros para el reporte
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ventaId", ventaId);

            System.out.println("➡ Generando factura para ventaId = " + ventaId);

            // 5. Llenar el reporte con la BD y los parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, conn);

            // 6. Preparar respuesta HTTP
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=factura_venta_" + ventaId + ".pdf");

            outStream = response.getOutputStream();

            // 7. Exportar a PDF directamente al navegador
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
            outStream.flush();

        } catch (FileNotFoundException e) {
            System.err.println("📛 Archivo no encontrado: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (JRException e) {
            System.err.println("📛 Error JasperReports al generar el reporte: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (SQLException e) {
            System.err.println("📛 Error de conexión a base de datos: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            System.err.println("📛 Error de entrada/salida (IO): " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            System.err.println("❌ Error inesperado al generar la factura: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            try {
                if (inputStream != null) inputStream.close();
                if (outStream != null) outStream.close();
                if (conn != null) conn.close();
            } catch (IOException | SQLException e) {
                System.err.println("⚠️ Error al cerrar recursos: " + e.getMessage());
            }
        }
    }




}
