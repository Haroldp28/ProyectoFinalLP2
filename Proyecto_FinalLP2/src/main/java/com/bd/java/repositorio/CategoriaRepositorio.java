package com.bd.java.repositorio;

import com.bd.jpa.modelo.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {
    // Métodos personalizados si necesitas
}
