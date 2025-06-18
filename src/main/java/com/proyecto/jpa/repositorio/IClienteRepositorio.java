package com.proyecto.jpa.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.proyecto.jpa.modelo.TblCliente;

public interface IClienteRepositorio extends JpaRepository<TblCliente, Integer> {

}
