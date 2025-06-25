package com.proyecto.jpa.servicio;

import java.util.List;

import com.proyecto.jpa.modelo.TblPedido;

public interface IPedidoServicio {
	
	//declaramos los metodos
	void RegistrarPedido(TblPedido tblped);
	void EliminarPedido(TblPedido tblped);
	List<TblPedido> ListadoPedidos();
	TblPedido BuscarPorId(Integer id);

}
