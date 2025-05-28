package com.gfu.gestioninventario.Controller;



import com.gfu.gestioninventario.DTOs.DetalleOrdenDTO;
import com.gfu.gestioninventario.DTOs.OrdenCompletaDTO;
import com.gfu.gestioninventario.DTOs.ProductoDTO;
import com.gfu.gestioninventario.Models.*;
import com.gfu.gestioninventario.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
                    form.getDetalles(), // Aquí ya vienen las fechas individuales por detalle
                    form.getUsuarioId(),
                    form.getTipoMovimientoId()
            );
            return "redirect:/compras/lista?exito=La orden fue registrada correctamente";
        } catch (Exception e) {
            e.printStackTrace(); // útil para depuración
            return "redirect:/compras/lista?error=Ocurrió un problema al registrar la orden";
        }
    }

    @GetMapping("/eliminar/{ordenId}")
    public String eliminarOrden(@PathVariable Integer ordenId) {
        try {
            OrdenCompra orden = ordenCompraService.obtenerPorId(ordenId);

            if (orden.getEstado() == EstadoOrden.PENDIENTE || orden.getEstado() == EstadoOrden.CANCELADO) {
                ordenCompraService.eliminarOrdenCompleta(ordenId);
                return "redirect:/compras/lista?exito=Orden eliminada correctamente";
            } else {
                return "redirect:/compras/lista?error=Solo puede eliminar órdenes en estado RECIBIDO Y CANCELADO";
            }
        } catch (Exception e) {
            return "redirect:/compras/lista?error=" + e.getMessage();
        }
    }



    @GetMapping("/api/detalle/{ordenId}")
    public ResponseEntity<DetalleOrdenDTO> obtenerDetalleOrden(@PathVariable Integer ordenId) {
        try {
            OrdenCompra orden = ordenCompraService.obtenerPorId(ordenId);
            if (orden == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            DetalleOrdenDTO dto = new DetalleOrdenDTO();
            dto.setProveedor(orden.getProveedor().getNombreProveedor());
            dto.setProveedorId(orden.getProveedor().getProveedorId());
            dto.setFechaOrden(orden.getFechaOrden());

            List<DetalleOrdenDTO.ItemDetalleDTO> lista = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


            for (DetalleOrdenCompra d : orden.getDetalles()) {
                DetalleOrdenDTO.ItemDetalleDTO item = new DetalleOrdenDTO.ItemDetalleDTO();
                item.setProducto(d.getProducto() != null ? d.getProducto().getNombre() : "Desconocido");
                item.setProductoId(d.getProducto().getProductoId());
                item.setCantidad(d.getCantidad());
                item.setPrecioUnitario(d.getPrecioUnitario());

                // Buscar el lote que coincide con este producto y esta orden
                Optional<Lote> loteRelacionado = orden.getLotes()
                        .stream()
                        .filter(l ->
                                l.getProducto().getProductoId().equals(d.getProducto().getProductoId())
                                        && l.getOrden().getOrdenId().equals(orden.getOrdenId())
                        )
                        .findFirst();

                if (loteRelacionado.isPresent()) {
                    Date fechaVenc = loteRelacionado.get().getFechaVencimiento();
                    item.setFechaVencimiento(fechaVenc != null ? new SimpleDateFormat("yyyy-MM-dd").format(fechaVenc) : "");
                } else {
                    item.setFechaVencimiento("");
                }

                lista.add(item);
            }

            dto.setDetalles(lista);
            return ResponseEntity.ok(dto);

        } catch (Exception e) {
            System.err.println("❌ Error en obtenerDetalleOrden:");
            System.err.println("Mensaje: " + e.getMessage());
            e.printStackTrace(); // <--- Deja esto
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }



    @PostMapping("/editar/{ordenId}")
    public String editarOrden(@PathVariable Integer ordenId,
                              @ModelAttribute OrdenCompletaDTO dto) {
        try {
            loteService.editarOrdenCompra(
                    ordenId,
                    dto.getFechaOrden(),
                    dto.getDetalles(),
                    dto.getUsuarioId(),
                    dto.getTipoMovimientoId(),
                    null // solo edición normal
            );
            return "redirect:/compras/lista?exito=Orden editada correctamente";
        } catch (Exception e) {
            return "redirect:/compras/lista?error=Error al editar la orden";
        }
    }

    @PostMapping("/recibir/{ordenId}")
    public ResponseEntity<?> recibirOrden(@PathVariable Integer ordenId,
                                          @ModelAttribute OrdenCompletaDTO dto) {
        try {
            loteService.editarOrdenCompra(
                    ordenId,
                    dto.getFechaOrden(),
                    dto.getDetalles(),
                    dto.getUsuarioId(),
                    dto.getTipoMovimientoId(),
                    "RECIBIR"
            );
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/cancelar/{ordenId}")
    public ResponseEntity<?> cancelarOrden(@PathVariable Integer ordenId,
                                           @ModelAttribute OrdenCompletaDTO dto) {
        try {
            loteService.editarOrdenCompra(
                    ordenId,
                    dto.getFechaOrden(),
                    dto.getDetalles(),
                    dto.getUsuarioId(),
                    dto.getTipoMovimientoId(),
                    "CANCELAR"
            );
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
    public List<ProductoDTO> listarProductosPorProveedor(@PathVariable Integer id) {
        return productoService.obtenerProductosPorProveedor(id);
    }

/*
    @GetMapping("/api/detalle/{ordenId}")
    @ResponseBody
    public DetalleOrdenDTO obtenerDetalleOrden(@PathVariable Integer ordenId) {
        OrdenCompra orden = ordenCompraService.obtenerPorId(ordenId);
        List<DetalleOrdenCompra> detalles = detalleOrdenCompraService.obtenerPorOrden(ordenId);

        DetalleOrdenDTO dto = new DetalleOrdenDTO();
        dto.setProveedor(orden.getProveedor().getNombreProveedor());
        dto.setFechaOrden(orden.getFechaOrden());
        dto.setFechaVencimiento(orden.getFechaVencimiento());

        List<DetalleOrdenDTO.ItemDetalleDTO> detalleItems = detalles.stream().map(d -> {
            DetalleOrdenDTO.ItemDetalleDTO item = new DetalleOrdenDTO.ItemDetalleDTO();
            item.setProducto(d.getProducto().getNombre());
            item.setCantidad(d.getCantidad());
            item.setPrecioUnitario(d.getPrecioUnitario());
            return item;
        }).toList();

        dto.setDetalles(detalleItems);
        return dto;
    }*/


}