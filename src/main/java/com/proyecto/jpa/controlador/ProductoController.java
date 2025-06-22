package com.proyecto.jpa.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.jpa.modelo.TblProducto;
import com.proyecto.jpa.servicio.ICategoriaServicio;
import com.proyecto.jpa.servicio.IProductoServicio;

@Controller
@RequestMapping("/vistas")
public class ProductoController {
	
	//inyeccion de dependencia
	@Autowired //ayuda a trabajar con la dependencia
	private IProductoServicio iproductoservicio;
	@Autowired
	private ICategoriaServicio icategoriaservicio;
	
	@GetMapping("ListadoProductos")
	public String ListadoProducto(Model modelo) {
		//recuperamos los datos de la BD
		List<TblProducto> listado=iproductoservicio.ListadoProductos();
		//lo enviamos a la vista
		modelo.addAttribute("listado", listado);
		//retornamos
		return "/vistas/ListadoProducto";
	}
	
	@GetMapping("RegistrarProducto")
	public String RegistrarProducto(Model modelo) {
		//realizamos la respectiva instancia....
		TblProducto prod=new TblProducto();
		//enviamos a la vista
		modelo.addAttribute("regproducto",prod);
		// Cargar la lista para el combo box
	    modelo.addAttribute("listacategorias", icategoriaservicio.ListarCategoria());
		//retornamos el formulario
		return "/vistas/RegistrarProducto";
		
		
	}
	
	@PostMapping("GuardarProducto")
	public String GuardarProducto (@ModelAttribute TblProducto prod,Model modelo) {
		
		iproductoservicio.RegistrarProducto(prod);
		//mensajito pe
		System.out.println("Se ha registrado correctamente en la BD");
		//retornamos a lista
		return "redirect:/vistas/ListadoProductos";
	}//fin del modelo

}//fin de la clase
