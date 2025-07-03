package com.bd.java.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bd.jpa.modelo.Pedido;

public interface PedidoRepositorio extends JpaRepository<Pedido, Long> {

}
