package com.bd.jpa.controlador;

import com.bd.jpa.modelo.Usuario;
import com.bd.jpa.servicio.UsuarioServicio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/home")
    public String mostrarDashboard(HttpSession session, Model model) {
        System.out.println("🔍 Entrando a /home");
        
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        System.out.println("🔍 Usuario logueado: " + (usuarioLogueado != null ? usuarioLogueado.getUsuario() : "null"));

        if (usuarioLogueado == null) {
            System.out.println("❌ No hay usuario en sesión, redirigiendo a login");
            return "redirect:/login";
        }

        try {
            System.out.println("🔍 Iniciando consultas de dashboard...");
            
            Long totalUsuarios = usuarioServicio.contarTotal();
            System.out.println("✅ Total usuarios: " + totalUsuarios);
            
            Long adminCount = usuarioServicio.contarPorRango("Administrador");
            System.out.println("✅ Admins: " + adminCount);
            
            Long empleadoCount = usuarioServicio.contarPorRango("Empleado");
            System.out.println("✅ Empleados: " + empleadoCount);
            
            Long supervisorCount = usuarioServicio.contarPorRango("Supervisor");
            System.out.println("✅ Supervisores: " + supervisorCount);

            Long otrosCount = totalUsuarios - (adminCount + empleadoCount + supervisorCount);

            var rangosUnicos = usuarioServicio.obtenerRangos();
            System.out.println("✅ Rangos únicos: " + rangosUnicos);
            
            var usuariosRecientes = usuarioServicio.obtenerUsuariosRecientes(5);
            System.out.println("✅ Usuarios recientes: " + usuariosRecientes.size());

            model.addAttribute("usuarioActual", usuarioLogueado);
            model.addAttribute("totalUsuarios", totalUsuarios);
            model.addAttribute("adminCount", adminCount);
            model.addAttribute("empleadoCount", empleadoCount);
            model.addAttribute("supervisorCount", supervisorCount);
            model.addAttribute("otrosCount", otrosCount);
            model.addAttribute("rangosUnicos", rangosUnicos);
            model.addAttribute("usuariosRecientes", usuariosRecientes);

            System.out.println("✅ Dashboard cargado para: " + usuarioLogueado.getUsuario());
            System.out.println("🔍 Retornando 'home'");
            return "vistas/home";

        } catch (Exception e) {
            System.err.println("❌ ERROR EN DASHBOARD: " + e.getMessage());
            e.printStackTrace(); // ← IMPORTANTE: Esto muestra toda la excepción
            model.addAttribute("error", "No se pudo cargar el dashboard");
            model.addAttribute("usuarioActual", usuarioLogueado);
            return "vistas/home";
        }
    }
}