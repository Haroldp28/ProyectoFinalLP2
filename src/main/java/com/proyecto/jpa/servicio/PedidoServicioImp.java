package com.proyecto.jpa.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.jpa.modelo.TblPedido;
import com.proyecto.jpa.repositorio.IPedidoRepositorio;

@Service
public class PedidoServicioImp implements IPedidoServicio {

    @Autowired
    private  IPedidoRepositorio iPedido;

    @Override
    public void RegistrarPedido(TblPedido pedido) {
        iPedido.save(pedido);
    }

    @Override
    public void EliminarPedido(TblPedido pedido) {
       iPedido.delete(pedido);
    }

    @Override
    public List<TblPedido> ListarPedidos() {
        return (List<TblPedido>) iPedido.findAll();
    }

    @Override
    public TblPedido BuscarPedido(Integer id) {
        return iPedido.findById(id).orElse(null);
    }
}
