package com.gfu.gestioninventario.Controller;



import com.gfu.gestioninventario.DTOs.OrdenCompletaDTO;
import com.gfu.gestioninventario.Models.DetalleOrdenCompra;
import com.gfu.gestioninventario.Models.Lote;
import com.gfu.gestioninventario.Models.OrdenCompra;
import com.gfu.gestioninventario.Models.Producto;
import com.gfu.gestioninventario.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/compras")
public class ComprasController {

    @Autowired
    private OrdenCompraService ordenCompraService;

    @Autowired
    private DetalleOrdenCompraService detalleOrdenCompraService;

    @Autowired
    private LoteService loteService;

    @Autowired
    private ProductoService productoService;
    @Autowired
    private ProveedoresService proveedoresService;




    @PostMapping("/ordenCompleta")
    public String procesarOrdenCompleta(@ModelAttribute OrdenCompletaDTO form) {
        try {
            loteService.registrarLoteCompleto(
                    form.getProveedorId(),
                    form.getFechaOrden(),
                    form.getDetalles(),
                    form.getFechaVencimiento(),
                    form.getUsuarioId(),
                    form.getTipoMovimientoId()
            );
            return "redirect:/compras/lista?exito=La orden fue registrada correctamente";
        } catch (Exception e) {
            e.printStackTrace(); // opcional para ver error en consola
            return "redirect:/compras/lista?error=Ocurrió un problema al registrar la orden";
        }
    }




    @PostMapping("/editarLote")
    public String editarLoteDesdeDetalle(@RequestParam Integer ordenId,
                                         @RequestParam Integer productoId,
                                         @RequestParam Integer nuevaCantidad,
                                         @RequestParam Integer usuarioId,
                                         @RequestParam Integer tipoMovimientoId) {

        loteService.ajustarLotePorEdicion(ordenId, productoId, nuevaCantidad, usuarioId, tipoMovimientoId);
        return "redirect:/compras/ver/" + ordenId;
    }

    @GetMapping("/lista")
    public String listarOrdenesCompra(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "ordenId") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<OrdenCompra> paginaOrdenes = keyword.isEmpty() ?
                ordenCompraService.listarOrdenes(pageable) :
                ordenCompraService.buscarPorKeyword(keyword, pageable);

        model.addAttribute("ordenes", paginaOrdenes);
        model.addAttribute("keyword", keyword);
        model.addAttribute("size", size);
        model.addAttribute("paginaActual", page);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("sizeOptions", List.of(5, 10, 20, 50));

        model.addAttribute("proveedores", proveedoresService.listadeProveedores());

        return "compras/listado_ordenes";
    }



    @GetMapping("/ver/{ordenId}")
    public String verOrden(@PathVariable Integer ordenId, Model model) {
        OrdenCompra orden = ordenCompraService.obtenerPorId(ordenId);
        List<DetalleOrdenCompra> detalles = detalleOrdenCompraService.obtenerPorOrden(ordenId);
        List<Lote> lotes = loteService.listarPorOrden(ordenId);

        model.addAttribute("orden", orden);
        model.addAttribute("detalles", detalles);
        model.addAttribute("lotes", lotes);
        return "compras/ver_orden";
    }


    @GetMapping("/productos/proveedor/{id}")
    @ResponseBody
    public List<Producto> listarProductosPorProveedor(@PathVariable Integer id) {
        return productoService.findByProveedorId(id);
    }

}