package com.proyecto.jpa.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.jpa.modelo.TblCategoria;
import com.proyecto.jpa.repositorio.ICategoriaRepositorio;

@Service
public class CategoriaServicioImp implements ICategoriaServicio {

    @Autowired
    private ICategoriaRepositorio icategoriaRepositorio;

    @Override
    public List<TblCategoria> ListarCategoria() {
        return (List<TblCategoria>) icategoriaRepositorio.findAll();
    }
}