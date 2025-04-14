package com.gfu.gestioninventario.Services;

import com.gfu.gestioninventario.Models.*;
import com.gfu.gestioninventario.Repository.DetalleOrdenCompraRepository;
import com.gfu.gestioninventario.Repository.LoteRepository;
import com.gfu.gestioninventario.Repository.OrdenCompraRepository;
import com.gfu.gestioninventario.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenCompraService {

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;
    
    @Autowired
    private DetalleOrdenCompraRepository detalleOrdenCompraRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private LoteRepository loteRepository;
    
    @Autowired
    private ProveedoresService proveedoresService;

    /**
     * Crea una nueva orden de compra con sus detalles
     * @param ordenCompra La orden de compra a crear
     * @param detalles Lista de detalles de la orden
     * @return La orden de compra guardada
     */
    @Transactional
    public OrdenCompra crearOrdenCompra(OrdenCompra ordenCompra, List<DetalleOrdenCompra> detalles) {
        // Validar proveedor
        if(ordenCompra.getProveedor() == null || ordenCompra.getProveedor().getProveedorId() == null) {
            throw new IllegalArgumentException("Se debe especificar un proveedor válido");
        }
        
        // Verificar que el proveedor existe
        proveedoresService.BuscarporID(ordenCompra.getProveedor().getProveedorId())
            .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        
        // Configurar valores iniciales
        ordenCompra.setFechaOrden(new Date());
        ordenCompra.setEstado(EstadoOrden.PENDIENTE);
        
        // Guardar la orden principal
        OrdenCompra ordenGuardada = ordenCompraRepository.save(ordenCompra);
        
        // Guardar los detalles y calcular subtotal
        for(DetalleOrdenCompra detalle : detalles) {
            // Validar producto
            Producto producto = productoRepository.findById(detalle.getProducto().getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detalle.getProducto().getProductoId()));
            
            detalle.setOrden(ordenGuardada);
            detalle.setProducto(producto);
            
            // Validar que el precio unitario sea positivo
            if(detalle.getPrecioUnitario() <= 0) {
                throw new IllegalArgumentException("El precio unitario debe ser mayor que cero");
            }
            
            // Validar que la cantidad sea positiva
            if(detalle.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
            }
            
            detalleOrdenCompraRepository.save(detalle);
        }
        
        return ordenGuardada;
    }

    /**
     * Obtiene todas las órdenes de compra
     * @return Lista de órdenes de compra
     */
    public List<OrdenCompra> obtenerTodasOrdenes() {
        return ordenCompraRepository.findAll();
    }

    /**
     * Busca una orden de compra por su ID
     * @param id ID de la orden
     * @return La orden encontrada (opcional)
     */
    public Optional<OrdenCompra> obtenerOrdenPorId(Integer id) {
        return ordenCompraRepository.findById(id);
    }

    /**
     * Actualiza el estado de una orden de compra
     * @param id ID de la orden
     * @param nuevoEstado Nuevo estado a asignar
     * @return La orden actualizada
     */
    @Transactional
    public OrdenCompra actualizarEstadoOrden(Integer id, EstadoOrden nuevoEstado) {
        OrdenCompra orden = ordenCompraRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + id));
        
        // Validar transición de estado
        if(orden.getEstado() == EstadoOrden.CANCELADO) {
            throw new IllegalStateException("No se puede modificar una orden cancelada");
        }
        
        if(nuevoEstado == EstadoOrden.RECIBIDO && orden.getEstado() == EstadoOrden.PENDIENTE) {
            // Al recibir la orden, crear los lotes correspondientes
            crearLotesParaOrden(orden);
        }
        
        orden.setEstado(nuevoEstado);
        return ordenCompraRepository.save(orden);
    }

    /**
     * Cancela una orden de compra
     * @param id ID de la orden a cancelar
     */
    public void cancelarOrden(Integer id) {
        OrdenCompra orden = ordenCompraRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + id));
        
        if(orden.getEstado() == EstadoOrden.RECIBIDO) {
            throw new IllegalStateException("No se puede cancelar una orden ya recibida");
        }
        
        orden.setEstado(EstadoOrden.CANCELADO);
        ordenCompraRepository.save(orden);
    }

    /**
     * Calcula el subtotal de una orden de compra
     * @param id ID de la orden
     * @return El subtotal calculado
     */
    public Double calcularSubtotalOrden(Integer id) {
        OrdenCompra orden = ordenCompraRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + id));
        
        return detalleOrdenCompraRepository.findByOrden(orden).stream()
            .mapToDouble(d -> d.getCantidad() * d.getPrecioUnitario())
            .sum();
    }

    /**
     * Crea lotes para los productos de una orden recibida
     * @param orden La orden de compra
     */
    private void crearLotesParaOrden(OrdenCompra orden) {
        List<DetalleOrdenCompra> detalles = detalleOrdenCompraRepository.findByOrden(orden);
        
        for(DetalleOrdenCompra detalle : detalles) {
            Lote lote = new Lote();
            lote.setOrden(orden);
            lote.setProducto(detalle.getProducto());
            lote.setCantidad(detalle.getCantidad());
            lote.setCostoUnitario(detalle.getPrecioUnitario());
            lote.setFechaIngreso(new Date());
            
            // Para productos perecederos, podríamos calcular una fecha de vencimiento
            // Aquí un ejemplo simple (30 días después)
            // En un sistema real, esto debería basarse en las características del producto
            // lote.setFechaVencimiento(calcularFechaVencimiento(detalle.getProducto()));
            
            lote.setEstado(true); // Lote activo
            
            loteRepository.save(lote);
        }
    }

    /**
     * Obtiene los detalles de una orden de compra
     * @param ordenId ID de la orden
     * @return Lista de detalles
     */
    public List<DetalleOrdenCompra> obtenerDetallesOrden(Integer ordenId) {
        OrdenCompra orden = ordenCompraRepository.findById(ordenId)
            .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + ordenId));
        
        return detalleOrdenCompraRepository.findByOrden(orden);
    }
}
