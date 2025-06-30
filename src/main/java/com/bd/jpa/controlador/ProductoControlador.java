package com.bd.jpa.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductoControlador {
    @GetMapping("/productos")
    public String productos() { return "productos"; }
}
