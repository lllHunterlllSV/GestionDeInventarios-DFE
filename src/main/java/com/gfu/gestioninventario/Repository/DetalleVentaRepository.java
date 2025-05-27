package com.gfu.gestioninventario.Repository;

import com.gfu.gestioninventario.Models.DetalleVenta;
import com.gfu.gestioninventario.Models.Producto;
import com.gfu.gestioninventario.Models.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Integer> {

    // Buscar detalles por venta
    List<DetalleVenta> findByVenta(Venta venta);

    // Buscar detalles por ID de venta
    List<DetalleVenta> findByVentaVentaId(Integer ventaId);

    // Buscar detalles por producto
    List<DetalleVenta> findByProducto(Producto producto);

    // Buscar detalles por ID de producto
    List<DetalleVenta> findByProductoProductoId(Integer productoId);

    // Buscar por venta y producto específicos
    Optional<DetalleVenta> findByVentaAndProducto(Venta venta, Producto producto);

    // Buscar detalles con cantidad mayor a un valor
    List<DetalleVenta> findByCantidadGreaterThan(Integer cantidad);

    // Buscar detalles con precio unitario mayor a un valor
    List<DetalleVenta> findByPrecioUnitarioGreaterThan(BigDecimal precio);

    // Buscar detalles con precio unitario entre dos valores
    List<DetalleVenta> findByPrecioUnitarioBetween(BigDecimal precioMin, BigDecimal precioMax);

    // Contar detalles por venta
    Long countByVenta(Venta venta);

    // Contar detalles por producto
    Long countByProducto(Producto producto);

    // Verificar si existe un detalle para una venta y producto específicos
    boolean existsByVentaAndProducto(Venta venta, Producto producto);

    // Queries personalizadas con @Query

    // Calcular el total de una venta específica
    @Query("SELECT SUM(d.cantidad * d.precioUnitario) FROM DetalleVenta d WHERE d.venta.ventaId = :ventaId")
    BigDecimal calcularTotalVenta(@Param("ventaId") Integer ventaId);

    // Obtener la cantidad total vendida de un producto
    @Query("SELECT SUM(d.cantidad) FROM DetalleVenta d WHERE d.producto.productoId = :productoId")
    Integer obtenerCantidadTotalVendidaProducto(@Param("productoId") Integer productoId);

    // Obtener los productos más vendidos (top N)
    @Query("SELECT d.producto, SUM(d.cantidad) as total FROM DetalleVenta d " +
            "GROUP BY d.producto ORDER BY total DESC")
    List<Object[]> obtenerProductosMasVendidos();

    // Obtener detalles de venta con información completa
    @Query("SELECT d FROM DetalleVenta d " +
            "JOIN FETCH d.venta v " +
            "JOIN FETCH d.producto p " +
            "WHERE v.ventaId = :ventaId")
    List<DetalleVenta> obtenerDetallesCompletos(@Param("ventaId") Integer ventaId);

    // Obtener el promedio de precio unitario por producto
    @Query("SELECT AVG(d.precioUnitario) FROM DetalleVenta d WHERE d.producto.productoId = :productoId")
    BigDecimal obtenerPromedioPrecioProducto(@Param("productoId") Integer productoId);

    // Obtener detalles que tienen devoluciones
    @Query("SELECT DISTINCT d FROM DetalleVenta d WHERE SIZE(d.devoluciones) > 0")
    List<DetalleVenta> obtenerDetallesConDevoluciones();

    // Obtener detalles sin devoluciones
    @Query("SELECT d FROM DetalleVenta d WHERE SIZE(d.devoluciones) = 0")
    List<DetalleVenta> obtenerDetallesSinDevoluciones();

    // Eliminar detalles por venta (útil para operaciones de cascade manual)
    void deleteByVenta(Venta venta);

    // Eliminar detalles por ID de venta
    void deleteByVentaVentaId(Integer ventaId);

    @Modifying
    @Query("DELETE FROM DetalleVenta d WHERE d.venta.ventaId = :ventaId")
    void deleteByVentaId(@Param("ventaId") Integer ventaId);

}