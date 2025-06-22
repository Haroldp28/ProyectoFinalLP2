package com.proyecto.jpa.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.jpa.modelo.TblCategoria;

public interface ICategoriaRepositorio extends JpaRepository<TblCategoria,Integer> {
	
	

}
