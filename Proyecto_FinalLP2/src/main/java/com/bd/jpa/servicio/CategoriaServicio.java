package com.bd.jpa.servicio;

import com.bd.java.repositorio.CategoriaRepositorio;
import com.bd.jpa.modelo.Categoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServicio {
    
    @Autowired
    private CategoriaRepositorio categoriaRepository;
    
    public List<Categoria> obtenerTodas() {
        return categoriaRepository.findAll();
    }
    
    public Optional<Categoria> obtenerPorId(Long id) {
        return categoriaRepository.findById(id);
    }
    
    public Categoria guardar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }
    
    public void eliminar(Long id) {
        categoriaRepository.deleteById(id);
    }
    
    public boolean existe(Long id) {
        return categoriaRepository.existsById(id);
    }
}
