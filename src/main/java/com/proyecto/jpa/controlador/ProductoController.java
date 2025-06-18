package com.proyecto.jpa.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.jpa.modelo.TblProducto;
import com.proyecto.jpa.servicio.IProductoServicio;

@Controller
@RequestMapping("/vistas")
public class ProductoController {
	
	//inyeccion de dependencia
	@Autowired //ayuda a trabajar con la dependencia
	private IProductoServicio iproductoservicio;
	
	@GetMapping("ListadoProductos")
	public String ListadoProducto(Model modelo) {
		//recuperamos los datos de la BD
		List<TblProducto> listado=iproductoservicio.ListadoProductos();
		//lo enviamos a la vista
		modelo.addAttribute("listado", listado);
		//retornamos
		return "/vistas/ListadoProducto";
	}

}
