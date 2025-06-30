package com.bd.java.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bd.jpa.modelo.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    // Métodos básicos de JpaRepository (findAll() está incluido automáticamente)
    
    // Buscar por campo usuario
    Optional<Usuario> findByUsuario(String usuario);

    // Verificar si existe usuario
    boolean existsByUsuario(String usuario);

    // Buscar por rango
    List<Usuario> findByRango(String rango);

    // Búsqueda general (usuario o rango)
    @Query("SELECT u FROM Usuario u WHERE " +
           "LOWER(u.usuario) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
           "LOWER(u.rango) LIKE LOWER(CONCAT('%', :busqueda, '%'))")
    List<Usuario> buscarUsuarios(@Param("busqueda") String busqueda);

    // Autenticación para login
    @Query("SELECT u FROM Usuario u WHERE u.usuario = :usuario AND u.password = :password")
    Optional<Usuario> autenticar(@Param("usuario") String usuario, @Param("password") String password);

    // Contar usuarios por rango
    long countByRango(String rango);

    // Obtener rangos únicos
    @Query("SELECT DISTINCT u.rango FROM Usuario u ORDER BY u.rango")
    List<String> findRangosUnicos();

    // Buscar usuarios ordenados por ID descendente (más recientes primero)
    @Query("SELECT u FROM Usuario u ORDER BY u.idusuario DESC")
    List<Usuario> findAllOrderByIdDesc();
    
    // Verificar si existe usuario con nombre diferente (para edición)
    @Query("SELECT COUNT(u) > 0 FROM Usuario u WHERE u.usuario = :usuario AND u.idusuario != :id")
    boolean existsByUsuarioAndIdNot(@Param("usuario") String usuario, @Param("id") Long id);
}