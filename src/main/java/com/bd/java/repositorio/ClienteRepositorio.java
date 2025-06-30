
package com.bd.java.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.bd.jpa.modelo.Cliente;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
    
    // Buscar cliente por DNI
    Optional<Cliente> findByDni(String dni);
    
    // Buscar cliente por email
    Optional<Cliente> findByEmail(String email);
    
    // Buscar cliente por teléfono
    Optional<Cliente> findByTelefono(String telefono);
    
    // Buscar clientes activos
    List<Cliente> findByActivoTrue();
    
    // Buscar clientes inactivos
    List<Cliente> findByActivoFalse();
    
    // Buscar por nombre (búsqueda parcial)
    @Query("SELECT c FROM Cliente c WHERE c.nombre LIKE %:nombre%")
    List<Cliente> findByNombreContaining(@Param("nombre") String nombre);
    
    // Buscar por apellido (búsqueda parcial)
    @Query("SELECT c FROM Cliente c WHERE c.apellido LIKE %:apellido%")
    List<Cliente> findByApellidoContaining(@Param("apellido") String apellido);
    
    // Buscar por nombre completo (nombre o apellido)
    @Query("SELECT c FROM Cliente c WHERE c.nombre LIKE %:termino% OR c.apellido LIKE %:termino%")
    List<Cliente> findByNombreCompletoContaining(@Param("termino") String termino);
    
    // Buscar por DNI, email o teléfono
    @Query("SELECT c FROM Cliente c WHERE c.dni = :valor OR c.email = :valor OR c.telefono = :valor")
    List<Cliente> findByDniEmailTelefono(@Param("valor") String valor);
    
    // Verificar si existe DNI
    boolean existsByDni(String dni);
    
    // Verificar si existe email
    boolean existsByEmail(String email);
    
    // Obtener clientes ordenados por nombre
    @Query("SELECT c FROM Cliente c ORDER BY c.nombre, c.apellido")
    List<Cliente> findAllOrderByNombre();
    
    // Obtener clientes activos ordenados
    @Query("SELECT c FROM Cliente c WHERE c.activo = true ORDER BY c.nombre, c.apellido")
    List<Cliente> findActivosOrderByNombre();
    
    // Buscar clientes registrados en un rango de fechas
    @Query("SELECT c FROM Cliente c WHERE c.fechaRegistro BETWEEN :fechaInicio AND :fechaFin")
    List<Cliente> findByFechaRegistroBetween(@Param("fechaInicio") java.time.LocalDateTime fechaInicio, 
                                           @Param("fechaFin") java.time.LocalDateTime fechaFin);
}