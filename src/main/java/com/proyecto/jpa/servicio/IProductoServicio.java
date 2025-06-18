package com.proyecto.jpa.servicio;

import java.util.List;

import com.proyecto.jpa.modelo.TblProducto;

public interface IProductoServicio {
	
	//declaramos los metodos
	void RegistrarProducto(TblProducto tblprod);
	void EliminarProducto(TblProducto tblprod);
	List<TblProducto> ListadoProductos();
	TblProducto BuscarPorId(Integer id);

}
