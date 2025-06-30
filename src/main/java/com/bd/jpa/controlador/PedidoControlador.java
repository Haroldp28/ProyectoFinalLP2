package com.bd.jpa.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PedidoControlador {
    @GetMapping("/pedidos")
    public String pedidos() { return "pedidos"; }
}
