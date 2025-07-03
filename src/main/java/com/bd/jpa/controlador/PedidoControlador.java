package com.bd.jpa.controlador;

import com.bd.jpa.modelo.Pedido;
import com.bd.jpa.modelo.Usuario;
import com.bd.jpa.servicio.PedidoServicio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class PedidoControlador {

    @Autowired
    private PedidoServicio pedidoService;

    @GetMapping("/pedidos")
    public String mostrarPedidos(HttpSession session, Model model) {
        System.out.println("🔍 Entrando a /pedidos");
        
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        try {
            List<Pedido> pedidos = pedidoService.obtenerTodos();
            
            model.addAttribute("usuarioActual", usuarioLogueado);
            model.addAttribute("pedidos", pedidos);
            
            System.out.println("✅ Pedidos cargados: " + pedidos.size());
            return "vistas/pedidos";
            
        } catch (Exception e) {
            System.err.println("❌ Error al cargar pedidos: " + e.getMessage());
            model.addAttribute("error", "Error al cargar los pedidos");
            return "vistas/pedidos";
        }
    }

    @GetMapping("/pedidos/nuevo")
    public String nuevoPedido(HttpSession session, Model model) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("usuarioActual", usuarioLogueado);
        model.addAttribute("pedido", new Pedido());
        
        return "vistas/pedido-form";
    }

    @PostMapping("/pedidos/guardar")
    public String guardarPedido(@ModelAttribute Pedido pedido, 
                                 HttpSession session, 
                                 RedirectAttributes redirectAttributes) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        try {
            pedidoService.guardar(pedido);
            
            if (pedido.getIdpedido() == null) {
                redirectAttributes.addFlashAttribute("success", "Pedido creado exitosamente");
                System.out.println("✅ Nuevo pedido creado: " + pedido.getProducto().getNombre());
            } else {
                redirectAttributes.addFlashAttribute("success", "Pedido actualizado exitosamente");
                System.out.println("✅ Pedido actualizado: " + pedido.getIdpedido());
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el pedido");
            System.err.println("❌ Error al guardar pedido: " + e.getMessage());
        }
        
        return "redirect:/pedidos";
    }

    @GetMapping("/pedidos/editar/{id}")
    public String editarPedido(@PathVariable Long id, 
                                HttpSession session, 
                                Model model, 
                                RedirectAttributes redirectAttributes) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        Optional<Pedido> pedido = pedidoService.obtenerPorId(id);
        
        if (pedido.isPresent()) {
            model.addAttribute("usuarioActual", usuarioLogueado);
            model.addAttribute("pedido", pedido.get());
            return "vistas/pedido-form";
        } else {
            redirectAttributes.addFlashAttribute("error", "Pedido no encontrado");
            return "redirect:/pedidos";
        }
    }

    @PostMapping("/pedidos/eliminar/{id}")
    public String eliminarPedido(@PathVariable Long id, 
                                  HttpSession session, 
                                  RedirectAttributes redirectAttributes) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        try {
            if (pedidoService.existe(id)) {
                pedidoService.eliminarPorId(id);
                redirectAttributes.addFlashAttribute("success", "Pedido eliminado exitosamente");
                System.out.println("✅ Pedido eliminado: ID " + id);
            } else {
                redirectAttributes.addFlashAttribute("error", "Pedido no encontrado");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el pedido");
            System.err.println("❌ Error al eliminar pedido: " + e.getMessage());
        }
        
        return "redirect:/pedidos";
    }
}