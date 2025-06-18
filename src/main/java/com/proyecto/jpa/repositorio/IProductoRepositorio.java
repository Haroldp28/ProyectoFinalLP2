package com.proyecto.jpa.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.jpa.modelo.TblProducto;

public interface IProductoRepositorio extends JpaRepository<TblProducto,Integer> {
	
	 @Override
	    @EntityGraph(attributePaths = {"categoria"})
	    List<TblProducto> findAll();
	
	
}
