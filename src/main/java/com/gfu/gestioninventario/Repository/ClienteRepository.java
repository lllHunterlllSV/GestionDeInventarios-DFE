package com.gfu.gestioninventario.Repository;

import com.gfu.gestioninventario.Models.Cliente;
import com.gfu.gestioninventario.Models.TipoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    // Buscar por NIF o DUI
    Optional<Cliente> findByNIF(String nif);
    Optional<Cliente> findByDUI(String dui);

    // Buscar clientes por tipo
    List<Cliente> findByTipoCliente(TipoCliente tipoCliente);

    // Buscar por email
    Optional<Cliente> findByEmail(String email);

    // Buscar por nombre (parcial/contiene)
    List<Cliente> findByNombreClienteContainingIgnoreCase(String nombreCliente);

    // Buscar solo clientes activos   ////procedimiento almacenado
    @Query("SELECT c FROM Cliente c WHERE c.estado = true")
    List<Cliente> findAllActiveClients();

    // Verificar si existe un cliente con NIF o DUI específico
    boolean existsByNIF(String nif);
    boolean existsByDUI(String dui);
    boolean existsByEmail(String email);

    // Buscar por teléfono
    Optional<Cliente> findByTelefono(Integer telefono);

    // Búsqueda personalizada por varios campos                          ////procedimiento almacenado
    @Query("SELECT c FROM Cliente c WHERE " +
            "(:nombre IS NULL OR LOWER(c.nombreCliente) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
            "(:tipoClienteId IS NULL OR c.tipoCliente.tipoClienteId = :tipoClienteId) AND " +
            "(:estado IS NULL OR c.estado = :estado)")
    List<Cliente> buscarClientes(
            @Param("nombre") String nombre,
            @Param("tipoClienteId") Integer tipoClienteId,
            @Param("estado") Boolean estado
    );
}