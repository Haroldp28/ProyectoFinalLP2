package com.bd.jpa.servicio;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bd.jpa.modelo.Pedido;
import com.bd.java.repositorio.PedidoRepositorio;



@Service
public class PedidoServicio {

    @Autowired
    private  PedidoRepositorio pedidorepositorio;

    public List<Pedido> obtenerTodos() {
        return pedidorepositorio.findAll();
    }
    
    public Pedido guardar(Pedido pedido) {
        return pedidorepositorio.save(pedido);
    }
    
    public Optional<Pedido> obtenerPorId(Long id) {
        return pedidorepositorio.findById(id);
    }
    
    
    public boolean existe(Long id) {
        return pedidorepositorio.existsById(id);
    }

    
    public void eliminarPedido(Pedido pedido) {
       pedidorepositorio.delete(pedido);
    }
    
    public void eliminarPorId(Long id) {
        pedidorepositorio.deleteById(id);
    }

   
    public Pedido BuscarPorId(Long id) {
        return pedidorepositorio.findById(id).orElse(null);
    }
}
