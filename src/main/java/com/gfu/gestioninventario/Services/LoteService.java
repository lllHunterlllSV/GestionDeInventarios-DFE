package com.gfu.gestioninventario.Services;

import com.gfu.gestioninventario.Models.Lote;
import com.gfu.gestioninventario.Repository.LoteRepository;
import com.gfu.gestioninventario.Repository.OrdenCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class LoteService {

    @Autowired
    private LoteRepository loteRepository;

    /**
     * Crea un nuevo lote de productos
     * @param lote El lote a crear
     * @return El lote creado
     */
    public Lote crearLote(Lote lote) {
        // Validaciones básicas
        if(lote.getProducto() == null || lote.getProducto().getProductoId() == null) {
            throw new IllegalArgumentException("El lote debe estar asociado a un producto válido");
        }
        
        if(lote.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }

        if (lote.getCostoUnitario().compareTo(BigDecimal.valueOf(0.00)) <= 0) {

            throw new IllegalArgumentException("El costo unitario debe ser mayor que cero");
        }

        
        return loteRepository.save(lote);
    }

    /**
     * Obtiene todos los lotes
     * @return Lista de lotes
     */
    public List<Lote> obtenerTodosLotes() {
        return loteRepository.findAll();
    }

    /**
     * Busca un lote por su ID
     * @param id ID del lote
     * @return El lote encontrado (opcional)
     */
    public Optional<Lote> obtenerLotePorId(Integer id) {
        return loteRepository.findById(id);
    }

    /**
     * Actualiza un lote existente
     * @param lote El lote con los datos actualizados
     * @return El lote actualizado
     */
    public Lote actualizarLote(Lote lote) {
        if(!loteRepository.existsById(lote.getLoteId())) {
            throw new RuntimeException("Lote no encontrado con ID: " + lote.getLoteId());
        }
        
        return loteRepository.save(lote);
    }

    /**
     * Elimina un lote
     * @param id ID del lote a eliminar
     */
    public void eliminarLote(Integer id) {
        if(!loteRepository.existsById(id)) {
            throw new RuntimeException("Lote no encontrado con ID: " + id);
        }
        
        // Validar que el lote no esté asociado a movimientos
        // (esto requeriría una consulta adicional)
        
        loteRepository.deleteById(id);
    }

    /**
     * Obtiene los lotes de un producto específico
     * @param productoId ID del producto
     * @return Lista de lotes del producto
     */
    public List<Lote> obtenerLotesPorProducto(Integer productoId) {
        return loteRepository.findByProductoProductoId(productoId);
    }

    /**
     * Obtiene los lotes activos (no vencidos y con cantidad > 0)
     * @return Lista de lotes activos
     */
    public List<Lote> obtenerLotesActivos() {
        return loteRepository.findByEstadoTrue();
    }
}
