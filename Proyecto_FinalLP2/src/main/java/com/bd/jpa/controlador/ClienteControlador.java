package com.bd.jpa.controlador;

import com.bd.jpa.modelo.Cliente;
import com.bd.jpa.modelo.Usuario;
import com.bd.jpa.servicio.ClienteServicio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class ClienteControlador {

    @Autowired
    private ClienteServicio clienteServicio;

    @GetMapping("/clientes")
    public String mostrarClientes(HttpSession session, Model model) {
        System.out.println("🔍 Entrando a /clientes");
        
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        try {
            List<Cliente> clientes = clienteServicio.obtenerActivosOrdenados();
            
            model.addAttribute("usuarioActual", usuarioLogueado);
            model.addAttribute("clientes", clientes);
            model.addAttribute("totalClientes", clienteServicio.contarTotal());
            model.addAttribute("clientesActivos", clienteServicio.contarActivos());
            model.addAttribute("clientesInactivos", clienteServicio.contarInactivos());
            
            System.out.println("✅ Clientes cargados: " + clientes.size());
            return "vistas/clientes";
            
        } catch (Exception e) {
            System.err.println("❌ Error al cargar clientes: " + e.getMessage());
            model.addAttribute("error", "Error al cargar los clientes");
            return "vistas/clientes";
        }
    }

    @GetMapping("/clientes/nuevo")
    public String nuevoCliente(HttpSession session, Model model) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("usuarioActual", usuarioLogueado);
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("accion", "nuevo");
        
        return "vistas/cliente-form";
    }

    @PostMapping("/clientes/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente, 
                               HttpSession session, 
                               RedirectAttributes redirectAttributes) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        try {
            if (cliente.getIdcliente() == null) {
                // Nuevo cliente
                clienteServicio.registrarCliente(
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.getTelefono(),
                    cliente.getEmail(),
                    cliente.getDireccion(),
                    cliente.getDni()
                );
                redirectAttributes.addFlashAttribute("success", "Cliente registrado exitosamente");
                System.out.println("✅ Nuevo cliente registrado: " + cliente.getNombreCompleto());
            } else {
                // Actualizar cliente existente
                clienteServicio.actualizar(cliente);
                redirectAttributes.addFlashAttribute("success", "Cliente actualizado exitosamente");
                System.out.println("✅ Cliente actualizado: " + cliente.getNombreCompleto());
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            System.err.println("❌ Error al guardar cliente: " + e.getMessage());
        }
        
        return "redirect:/clientes";
    }

    @GetMapping("/clientes/editar/{id}")
    public String editarCliente(@PathVariable Long id, 
                              HttpSession session, 
                              Model model, 
                              RedirectAttributes redirectAttributes) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        Optional<Cliente> cliente = clienteServicio.obtenerPorId(id);
        
        if (cliente.isPresent()) {
            model.addAttribute("usuarioActual", usuarioLogueado);
            model.addAttribute("cliente", cliente.get());
            model.addAttribute("accion", "editar");
            return "vistas/cliente-form";
        } else {
            redirectAttributes.addFlashAttribute("error", "Cliente no encontrado");
            return "redirect:/clientes";
        }
    }

    @GetMapping("/clientes/ver/{id}")
    public String verCliente(@PathVariable Long id, 
                           HttpSession session, 
                           Model model, 
                           RedirectAttributes redirectAttributes) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        Optional<Cliente> cliente = clienteServicio.obtenerPorId(id);
        
        if (cliente.isPresent()) {
            model.addAttribute("usuarioActual", usuarioLogueado);
            model.addAttribute("cliente", cliente.get());
            return "vistas/cliente-detalle";
        } else {
            redirectAttributes.addFlashAttribute("error", "Cliente no encontrado");
            return "redirect:/clientes";
        }
    }

    @PostMapping("/clientes/desactivar/{id}")
    public String desactivarCliente(@PathVariable Long id, 
                                  HttpSession session, 
                                  RedirectAttributes redirectAttributes) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        try {
            clienteServicio.desactivar(id);
            redirectAttributes.addFlashAttribute("success", "Cliente desactivado exitosamente");
            System.out.println("✅ Cliente desactivado: ID " + id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            System.err.println("❌ Error al desactivar cliente: " + e.getMessage());
        }
        
        return "redirect:/clientes";
    }

    @PostMapping("/clientes/activar/{id}")
    public String activarCliente(@PathVariable Long id, 
                               HttpSession session, 
                               RedirectAttributes redirectAttributes) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        try {
            clienteServicio.activar(id);
            redirectAttributes.addFlashAttribute("success", "Cliente activado exitosamente");
            System.out.println("✅ Cliente activado: ID " + id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            System.err.println("❌ Error al activar cliente: " + e.getMessage());
        }
        
        return "redirect:/clientes";
    }

    @PostMapping("/clientes/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id, 
                                HttpSession session, 
                                RedirectAttributes redirectAttributes) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        try {
            clienteServicio.eliminar(id);
            redirectAttributes.addFlashAttribute("success", "Cliente eliminado permanentemente");
            System.out.println("✅ Cliente eliminado: ID " + id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            System.err.println("❌ Error al eliminar cliente: " + e.getMessage());
        }
        
        return "redirect:/clientes";
    }

    @GetMapping("/clientes/buscar")
    public String buscarClientes(@RequestParam String termino, 
                               HttpSession session, 
                               Model model) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        try {
            List<Cliente> clientes = clienteServicio.buscarPorNombreCompleto(termino);
            
            model.addAttribute("usuarioActual", usuarioLogueado);
            model.addAttribute("clientes", clientes);
            model.addAttribute("termino", termino);
            model.addAttribute("esBusqueda", true);
            
            return "vistas/clientes";
        } catch (Exception e) {
            model.addAttribute("error", "Error en la búsqueda: " + e.getMessage());
            return "vistas/clientes";
        }
    }

    @GetMapping("/clientes/todos")
    public String mostrarTodosClientes(HttpSession session, Model model) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        try {
            List<Cliente> clientes = clienteServicio.obtenerTodosOrdenados();
            
            model.addAttribute("usuarioActual", usuarioLogueado);
            model.addAttribute("clientes", clientes);
            model.addAttribute("mostrarTodos", true);
            model.addAttribute("totalClientes", clienteServicio.contarTotal());
            model.addAttribute("clientesActivos", clienteServicio.contarActivos());
            model.addAttribute("clientesInactivos", clienteServicio.contarInactivos());
            
            return "vistas/clientes";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar todos los clientes: " + e.getMessage());
            return "vistas/clientes";
        }
    }
}
