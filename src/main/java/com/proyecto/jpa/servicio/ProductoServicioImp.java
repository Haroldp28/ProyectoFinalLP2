package com.proyecto.jpa.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.jpa.modelo.TblProducto;
import com.proyecto.jpa.repositorio.IProductoRepositorio;

@Service //Importante implementar la anotacion del Service
public class ProductoServicioImp implements IProductoServicio {
	
	//Aplicamos la inyeccion de dependencia
	@Autowired
	private IProductoRepositorio iproductorepositorio;
	

	@Override
	public void RegistrarProducto(TblProducto tblprod) {
		//Invocamos el metodo Registrar
		
		iproductorepositorio.save(tblprod);
		
	}// fin del metodo registrar 

	@Override
	public void EliminarProducto(TblProducto tblprod) {
		// invocamos al metodo eliminar
		
		iproductorepositorio.deleteById(tblprod.getIdproducto());
		
	}//fin del metodo eliminar

	@Override
	public List<TblProducto> ListadoProductos() {
		// invocamos al metodo listar
		
		return (List<TblProducto>) iproductorepositorio.findAll();
	} // fin del metodo listar

	@Override
	public TblProducto BuscarPorId(Integer id) {
		// invocamos al metodo buscar
		
		return iproductorepositorio.findById(id).orElse(null);
	} //fin del metodo buscar 

}
