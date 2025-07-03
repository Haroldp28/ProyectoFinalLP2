package com.bd.jpa.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bd.jpa.modelo.Usuario;
import com.bd.jpa.servicio.UsuarioServicio;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String mostrarLogin(HttpSession session) {
        if (session.getAttribute("usuarioLogueado") != null) {
            return "redirect:/home"; // redirige al dashboard si ya está logueado
        }
        return "vistas/login"; // o simplemente "login" si está directo en /templates
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String usuario,
                                 @RequestParam String password,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {

        if (usuario == null || usuario.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El usuario es obligatorio");
            return "redirect:/login";
        }

        if (password == null || password.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "La contraseña es obligatoria");
            return "redirect:/login";
        }

        Usuario usuarioAutenticado = usuarioServicio.autenticar(usuario.trim(), password);

        if (usuarioAutenticado != null) {
            session.setAttribute("usuarioLogueado", usuarioAutenticado);
            session.setAttribute("nombreUsuario", usuarioAutenticado.getUsuario());
            session.setAttribute("rangoUsuario", usuarioAutenticado.getRango());

            System.out.println("✅ Login: " + usuarioAutenticado.getUsuario() +
                               " (" + usuarioAutenticado.getRango() + ")");
            return "redirect:/home"; // REDIRECCIÓN AL DASHBOARD
        } else {
            redirectAttributes.addFlashAttribute("error", "Usuario o contraseña incorrectos");
            redirectAttributes.addFlashAttribute("usuario", usuario);
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("mensaje", "Sesión cerrada correctamente");
        return "redirect:/login";
    }
}
