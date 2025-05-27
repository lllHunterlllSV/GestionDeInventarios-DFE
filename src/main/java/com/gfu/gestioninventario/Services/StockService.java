package com.gfu.gestioninventario.Services;

import com.gfu.gestioninventario.DTOs.StockDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {

    @PersistenceContext
    private EntityManager entityManager;

    public StockResult buscarStockPorKeyword(String keyword, int page, int size) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("sp_buscar_stock_por_keyword");

        // Registrar los parámetros
        query.registerStoredProcedureParameter("p_keyword", String.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("p_page_number", Integer.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("p_page_size", Integer.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("p_total_records", Long.class, jakarta.persistence.ParameterMode.OUT);

        // Setear valores
        query.setParameter("p_keyword", keyword);
        query.setParameter("p_page_number", page);
        query.setParameter("p_page_size", size);

        // Ejecutar
        List<Object[]> resultados = query.getResultList();
        Long total = (Long) query.getOutputParameterValue("p_total_records");

        List<StockDTO> stocks = new ArrayList<>();
        for (Object[] fila : resultados) {
            StockDTO dto = new StockDTO();
            dto.setProductoId((Integer) fila[0]);
            dto.setProductoNombre((String) fila[1]);
            dto.setStock((Integer) fila[2]);
            dto.setNombreProveedor((String) fila[3]);
            dto.setCategoria((String) fila[4]);
            stocks.add(dto);
        }

        return new StockResult(stocks, total);
    }

    // Clase interna para el resultado paginado
    public static class StockResult {
        private List<StockDTO> stocks;
        private Long totalRecords;

        public StockResult(List<StockDTO> stocks, Long totalRecords) {
            this.stocks = stocks;
            this.totalRecords = totalRecords;
        }

        // Getters
        public List<StockDTO> getStocks() {
            return stocks;
        }

        public Long getTotalRecords() {
            return totalRecords;
        }
    }
}
