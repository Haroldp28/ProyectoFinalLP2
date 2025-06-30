package com.bd.jpa.controlador;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bd.jpa.modelo.Usuario;
import com.bd.jpa.servicio.UsuarioServicio;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/usuarios")
public class UsuarioControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    // Verificar autenticación
    private boolean verificarAutenticacion(HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("usuarioLogueado") == null) {
            redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión para acceder");
            return false;
        }
        return true;
    }
    
    
    // ================= READ - LISTAR USUARIOS =================

    @GetMapping
    public String listar(@RequestParam(value = "busqueda", required = false) String busqueda,
                        @RequestParam(value = "rango", required = false) String rango,
                        HttpSession session,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        
        if (!verificarAutenticacion(session, redirectAttributes)) {
            return "redirect:/login";
        }
        
        try {
            java.util.List<Usuario> usuarios;
            
            System.out.println("🔍 LISTANDO USUARIOS...");
            
            if (busqueda != null && !busqueda.trim().isEmpty()) {
                usuarios = usuarioServicio.buscar(busqueda);
                model.addAttribute("busqueda", busqueda);
                System.out.println("🔍 BÚSQUEDA: '" + busqueda + "' - " + usuarios.size() + " resultados");
            } else if (rango != null && !rango.trim().isEmpty()) {
                usuarios = usuarioServicio.listarPorRango(rango);
                model.addAttribute("rangoSeleccionado", rango);
                System.out.println("📂 FILTRO POR RANGO: '" + rango + "' - " + usuarios.size() + " usuarios");
            } else {
                usuarios = usuarioServicio.listarTodos();
                System.out.println("📋 LISTAR TODOS: " + usuarios.size() + " usuarios");
            }
            
            // Debug: imprimir cada usuario
            System.out.println("👥 USUARIOS ENCONTRADOS:");
            for (Usuario u : usuarios) {
                System.out.println("   - ID: " + u.getIdusuario() + ", Usuario: " + u.getUsuario() + ", Rango: " + u.getRango());
            }
            
            model.addAttribute("usuarios", usuarios);
            model.addAttribute("rangosUnicos", usuarioServicio.obtenerRangos());
            
            return "vistas/usuarios";
            
        } catch (Exception e) {
            System.err.println("❌ ERROR AL LISTAR: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Error al cargar la lista de usuarios: " + e.getMessage());
            model.addAttribute("usuarios", new java.util.ArrayList<>());
            model.addAttribute("rangosUnicos", new java.util.ArrayList<>());
            return "vistas/usuarios";
        }
    }
    
    
    // ================= CREATE - NUEVO USUARIO =================
    @GetMapping("/nuevo")
    public String nuevo(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        
        if (!verificarAutenticacion(session, redirectAttributes)) {
            return "redirect:/login";
        }
        
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("titulo", "Nuevo Usuario");
        model.addAttribute("rangosUnicos", usuarioServicio.obtenerRangos());
        
        System.out.println("➕ FORMULARIO NUEVO USUARIO");
        return "vistas/usuarios-form";
    }
    
    // ================= CREATE/UPDATE - GUARDAR USUARIO  =================
    
    @PostMapping("/guardar")
    public String guardar(@RequestParam("usuario") String nombreUsuario,
                         @RequestParam("password") String password,
                         @RequestParam("rango") String rango,
                         @RequestParam(value = "idusuario", required = false) Long idusuario,
                         HttpSession session,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        
        System.out.println("🔥 LLEGÓ AL MÉTODO GUARDAR");
        System.out.println("📝 DATOS RECIBIDOS:");
        System.out.println("   - Usuario: " + nombreUsuario);
        System.out.println("   - Password: " + (password != null && !password.isEmpty() ? "[DEFINIDA]" : "[VACÍA]"));
        System.out.println("   - Rango: " + rango);
        System.out.println("   - ID: " + idusuario);
        
        if (!verificarAutenticacion(session, redirectAttributes)) {
            return "redirect:/login";
        }
        
        try {
            // Crear objeto Usuario manualmente
            Usuario usuario = new Usuario();
            usuario.setIdusuario(idusuario); // null para nuevo usuario
            usuario.setUsuario(nombreUsuario != null ? nombreUsuario.trim() : null);
            usuario.setPassword(password != null ? password.trim() : null);
            usuario.setRango(rango != null ? rango.trim() : null);
            
            // Validaciones básicas
            if (usuario.getUsuario() == null || usuario.getUsuario().isEmpty()) {
                model.addAttribute("error", "El nombre de usuario es obligatorio");
                model.addAttribute("usuario", usuario);
                model.addAttribute("titulo", usuario.getIdusuario() == null ? "Nuevo Usuario" : "Editar Usuario");
                model.addAttribute("rangosUnicos", usuarioServicio.obtenerRangos());
                return "vistas/usuarios-form";
            }
            
            if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
                model.addAttribute("error", "La contraseña es obligatoria");
                model.addAttribute("usuario", usuario);
                model.addAttribute("titulo", usuario.getIdusuario() == null ? "Nuevo Usuario" : "Editar Usuario");
                model.addAttribute("rangosUnicos", usuarioServicio.obtenerRangos());
                return "vistas/usuarios-form";
            }
            
            if (usuario.getRango() == null || usuario.getRango().isEmpty()) {
                model.addAttribute("error", "El rango es obligatorio");
                model.addAttribute("usuario", usuario);
                model.addAttribute("titulo", usuario.getIdusuario() == null ? "Nuevo Usuario" : "Editar Usuario");
                model.addAttribute("rangosUnicos", usuarioServicio.obtenerRangos());
                return "vistas/usuarios-form";
            }
            
            // Validaciones adicionales
            if (usuario.getUsuario().length() < 3) {
                model.addAttribute("error", "El nombre de usuario debe tener al menos 3 caracteres");
                model.addAttribute("usuario", usuario);
                model.addAttribute("titulo", usuario.getIdusuario() == null ? "Nuevo Usuario" : "Editar Usuario");
                model.addAttribute("rangosUnicos", usuarioServicio.obtenerRangos());
                return "vistas/usuarios-form";
            }
            
            if (usuario.getPassword().length() < 3) {
                model.addAttribute("error", "La contraseña debe tener al menos 3 caracteres");
                model.addAttribute("usuario", usuario);
                model.addAttribute("titulo", usuario.getIdusuario() == null ? "Nuevo Usuario" : "Editar Usuario");
                model.addAttribute("rangosUnicos", usuarioServicio.obtenerRangos());
                return "vistas/usuarios-form";
            }
            
            // Validar caracteres permitidos en usuario
            if (!usuario.getUsuario().matches("^[a-zA-Z0-9.]+$")) {
                model.addAttribute("error", "El nombre de usuario solo puede contener letras, números y puntos");
                model.addAttribute("usuario", usuario);
                model.addAttribute("titulo", usuario.getIdusuario() == null ? "Nuevo Usuario" : "Editar Usuario");
                model.addAttribute("rangosUnicos", usuarioServicio.obtenerRangos());
                return "vistas/usuarios-form";
            }
            
            // Validar usuario único (solo para nuevos usuarios)
            if (usuario.getIdusuario() == null && usuarioServicio.existeUsuario(usuario.getUsuario())) {
                model.addAttribute("error", "Ya existe un usuario con ese nombre");
                model.addAttribute("usuario", usuario);
                model.addAttribute("titulo", "Nuevo Usuario");
                model.addAttribute("rangosUnicos", usuarioServicio.obtenerRangos());
                return "vistas/usuarios-form";
            }
            
            // Guardar usuario
            Usuario usuarioGuardado = usuarioServicio.guardar(usuario);
            
            if (usuarioGuardado != null) {
                String mensaje = usuario.getIdusuario() == null ? 
                    "Usuario '" + usuarioGuardado.getUsuario() + "' creado exitosamente" : 
                    "Usuario '" + usuarioGuardado.getUsuario() + "' actualizado exitosamente";
                
                redirectAttributes.addFlashAttribute("success", mensaje);
                
                System.out.println("✅ " + (usuario.getIdusuario() == null ? "CREADO" : "ACTUALIZADO") + 
                                 ": " + usuarioGuardado.getUsuario() + " [" + usuarioGuardado.getRango() + "]");
                System.out.println("📊 TOTAL USUARIOS EN BD: " + usuarioServicio.contarTotal());
                
                return "redirect:/usuarios";
            } else {
                model.addAttribute("error", "Error al guardar el usuario en la base de datos");
                model.addAttribute("usuario", usuario);
                model.addAttribute("titulo", usuario.getIdusuario() == null ? "Nuevo Usuario" : "Editar Usuario");
                model.addAttribute("rangosUnicos", usuarioServicio.obtenerRangos());
                return "vistas/usuarios-form";
            }
            
        } catch (Exception e) {
            System.err.println("❌ ERROR AL GUARDAR: " + e.getMessage());
            e.printStackTrace();
            
            // Crear usuario para mantener los datos en el formulario
            Usuario usuario = new Usuario();
            usuario.setIdusuario(idusuario);
            usuario.setUsuario(nombreUsuario);
            usuario.setPassword(""); // No mostrar la contraseña por seguridad
            usuario.setRango(rango);
            
            model.addAttribute("error", "Error interno al guardar el usuario: " + e.getMessage());
            model.addAttribute("usuario", usuario);
            model.addAttribute("titulo", idusuario == null ? "Nuevo Usuario" : "Editar Usuario");
            model.addAttribute("rangosUnicos", usuarioServicio.obtenerRangos());
            return "vistas/usuarios-form";
        }
    }
    
    // ================= UPDATE - EDITAR USUARIO =================
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id,
                        HttpSession session,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        
        if (!verificarAutenticacion(session, redirectAttributes)) {
            return "redirect:/login";
        }
        
        try {
            Optional<Usuario> usuario = usuarioServicio.buscarPorId(id);
            
            if (usuario.isPresent()) {
                Usuario usuarioEditar = usuario.get();
                // Por seguridad, no mostrar la contraseña actual
                usuarioEditar.setPassword("");
                
                model.addAttribute("usuario", usuarioEditar);
                model.addAttribute("titulo", "Editar Usuario");
                model.addAttribute("rangosUnicos", usuarioServicio.obtenerRangos());
                
                System.out.println("✏️ EDITANDO USUARIO: " + usuarioEditar.getUsuario() + " (ID: " + id + ")");
                return "vistas/usuarios-form";
            } else {
                redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
                return "redirect:/usuarios";
            }
            
        } catch (Exception e) {
            System.err.println("❌ ERROR AL EDITAR: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al cargar el usuario");
            return "redirect:/usuarios";
        }
    }
    
    // ================= READ - VER DETALLES =================
    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id,
                     HttpSession session,
                     Model model,
                     RedirectAttributes redirectAttributes) {
        
        if (!verificarAutenticacion(session, redirectAttributes)) {
            return "redirect:/login";
        }
        
        try {
            Optional<Usuario> usuario = usuarioServicio.buscarPorId(id);
            
            if (usuario.isPresent()) {
                model.addAttribute("usuario", usuario.get());
                
                System.out.println("👁️ VIENDO USUARIO: " + usuario.get().getUsuario() + " (ID: " + id + ")");
                return "vistas/usuarios-vista_detallada";
            } else {
                redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
                return "redirect:/usuarios";
            }
            
        } catch (Exception e) {
            System.err.println("❌ ERROR AL VER: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al cargar el usuario");
            return "redirect:/usuarios";
        }
    }
    
    // ================= DELETE - ELIMINAR USUARIO =================
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
        
        if (!verificarAutenticacion(session, redirectAttributes)) {
            return "redirect:/login";
        }
        
        try {
            // Verificar que no se elimine a sí mismo
            Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
            if (usuarioLogueado != null && usuarioLogueado.getIdusuario().equals(id)) {
                redirectAttributes.addFlashAttribute("error", "No puedes eliminar tu propio usuario");
                return "redirect:/usuarios";
            }
            
            // Verificar si puede eliminar (si es admin, debe haber más de uno)
            if (!usuarioServicio.puedeEliminar(id)) {
                redirectAttributes.addFlashAttribute("error", "No se puede eliminar el último administrador del sistema");
                return "redirect:/usuarios";
            }
            
            // Obtener datos del usuario antes de eliminar
            Optional<Usuario> usuarioAEliminar = usuarioServicio.buscarPorId(id);
            
            boolean eliminado = usuarioServicio.eliminar(id);
            
            if (eliminado) {
                redirectAttributes.addFlashAttribute("success", "Usuario eliminado exitosamente");
                if (usuarioAEliminar.isPresent()) {
                    System.out.println("🗑️ ELIMINADO: " + usuarioAEliminar.get().getUsuario() + " (ID: " + id + ")");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el usuario");
            }
            
        } catch (Exception e) {
            System.err.println("❌ ERROR AL ELIMINAR: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error interno al eliminar el usuario");
        }
        
        return "redirect:/usuarios";
    }
}