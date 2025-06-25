package com.proyecto.jpa.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.jpa.modelo.TblPedido;
import com.proyecto.jpa.servicio.IPedidoServicio;

@Controller
@RequestMapping("/vistas")
public class PedidoController {
	
	//inyeccion de dependencia
	@Autowired //ayuda a trabajar con la dependencia
	private IPedidoServicio ipedidoservicio;
	
	@GetMapping("ListadoPedidos")
	public String ListadoPedidos(Model modelo) {
		//recuperamos los datos de la BD
		List<TblPedido> listado=ipedidoservicio.ListadoPedidos();
		//lo enviamos a la vista
		modelo.addAttribute("listado", listado);
		//retornamos
		return "/vistas/ListadoPedido";
	}

}
