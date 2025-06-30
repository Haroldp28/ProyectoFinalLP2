package com.bd.jpa.controlador;

import com.bd.jpa.modelo.Categoria;
import com.bd.jpa.modelo.Usuario;
import com.bd.jpa.servicio.CategoriaServicio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class CategoriaControlador {

    @Autowired
    private CategoriaServicio categoriaService;

    @GetMapping("/categorias")
    public String mostrarCategorias(HttpSession session, Model model) {
        System.out.println("🔍 Entrando a /categorias");
        
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        try {
            List<Categoria> categorias = categoriaService.obtenerTodas();
            
            model.addAttribute("usuarioActual", usuarioLogueado);
            model.addAttribute("categorias", categorias);
            
            System.out.println("✅ Categorías cargadas: " + categorias.size());
            return "vistas/categorias";
            
        } catch (Exception e) {
            System.err.println("❌ Error al cargar categorías: " + e.getMessage());
            model.addAttribute("error", "Error al cargar las categorías");
            return "vistas/categorias";
        }
    }

    @GetMapping("/categorias/nueva")
    public String nuevaCategoria(HttpSession session, Model model) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("usuarioActual", usuarioLogueado);
        model.addAttribute("categoria", new Categoria());
        
        return "vistas/categoria-form";
    }

    @PostMapping("/categorias/guardar")
    public String guardarCategoria(@ModelAttribute Categoria categoria, 
                                 HttpSession session, 
                                 RedirectAttributes redirectAttributes) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        try {
            categoriaService.guardar(categoria);
            
            if (categoria.getIdcategoria() == null) {
                redirectAttributes.addFlashAttribute("success", "Categoría creada exitosamente");
                System.out.println("✅ Nueva categoría creada: " + categoria.getNombre());
            } else {
                redirectAttributes.addFlashAttribute("success", "Categoría actualizada exitosamente");
                System.out.println("✅ Categoría actualizada: " + categoria.getNombre());
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar la categoría");
            System.err.println("❌ Error al guardar categoría: " + e.getMessage());
        }
        
        return "redirect:/categorias";
    }

    @GetMapping("/categorias/editar/{id}")
    public String editarCategoria(@PathVariable Long id, 
                                HttpSession session, 
                                Model model, 
                                RedirectAttributes redirectAttributes) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        Optional<Categoria> categoria = categoriaService.obtenerPorId(id);
        
        if (categoria.isPresent()) {
            model.addAttribute("usuarioActual", usuarioLogueado);
            model.addAttribute("categoria", categoria.get());
            return "vistas/categoria-form";
        } else {
            redirectAttributes.addFlashAttribute("error", "Categoría no encontrada");
            return "redirect:/categorias";
        }
    }

    @PostMapping("/categorias/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Long id, 
                                  HttpSession session, 
                                  RedirectAttributes redirectAttributes) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        try {
            if (categoriaService.existe(id)) {
                categoriaService.eliminar(id);
                redirectAttributes.addFlashAttribute("success", "Categoría eliminada exitosamente");
                System.out.println("✅ Categoría eliminada: ID " + id);
            } else {
                redirectAttributes.addFlashAttribute("error", "Categoría no encontrada");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la categoría");
            System.err.println("❌ Error al eliminar categoría: " + e.getMessage());
        }
        
        return "redirect:/categorias";
    }
}