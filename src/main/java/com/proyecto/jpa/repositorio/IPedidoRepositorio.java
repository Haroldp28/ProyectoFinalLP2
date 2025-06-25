package com.proyecto.jpa.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.proyecto.jpa.modelo.TblPedido;

public interface IPedidoRepositorio extends JpaRepository<TblPedido, Integer> {

}
