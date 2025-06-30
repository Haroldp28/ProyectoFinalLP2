package com.bd.jpa.servicio;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bd.java.repositorio.UsuarioRepositorio;
import com.bd.jpa.modelo.Usuario;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    // ================= CRUD BÁSICO =================
    
    // CREATE - Guardar usuario
    public Usuario guardar(Usuario usuario) {
        try {
            System.out.println("💾 GUARDANDO USUARIO EN SERVICIO: " + usuario.getUsuario());
            
            // Validar datos antes de guardar
            validarUsuario(usuario);
            
            // Si es un nuevo usuario, verificar que no exista
            if (usuario.getIdusuario() == null && existeUsuario(usuario.getUsuario())) {
                throw new RuntimeException("Ya existe un usuario con ese nombre");
            }
            
            Usuario usuarioGuardado = usuarioRepositorio.save(usuario);
            System.out.println("✅ USUARIO GUARDADO EXITOSAMENTE - ID: " + usuarioGuardado.getIdusuario());
            
            return usuarioGuardado;
        } catch (Exception e) {
            System.err.println("❌ ERROR AL GUARDAR USUARIO: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al guardar usuario: " + e.getMessage());
        }
    }
    
    // READ - Listar todos los usuarios
    public List<Usuario> listarTodos() {
        try {
            System.out.println("📋 LISTANDO TODOS LOS USUARIOS DESDE REPOSITORIO...");
            
            // Usar findAllOrderByIdDesc para mostrar los más recientes primero
            List<Usuario> usuarios = usuarioRepositorio.findAllOrderByIdDesc();
            
            System.out.println("📊 USUARIOS ENCONTRADOS EN BD: " + usuarios.size());
            
            // Debug: mostrar cada usuario
            for (Usuario u : usuarios) {
                System.out.println("   📄 Usuario: " + u.getUsuario() + " (ID: " + u.getIdusuario() + ") [" + u.getRango() + "]");
            }
            
            return usuarios;
        } catch (Exception e) {
            System.err.println("❌ ERROR AL LISTAR USUARIOS: " + e.getMessage());
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }
    
    // READ - Buscar por ID
    public Optional<Usuario> buscarPorId(Long id) {
        try {
            System.out.println("🔍 BUSCANDO USUARIO POR ID: " + id);
            return usuarioRepositorio.findById(id);
        } catch (Exception e) {
            System.err.println("❌ ERROR AL BUSCAR USUARIO POR ID: " + e.getMessage());
            return Optional.empty();
        }
    }
    
    // UPDATE - Actualizar usuario
    public Usuario actualizar(Usuario usuario) {
        return guardar(usuario);  // save() funciona para crear y actualizar
    }
    
    // DELETE - Eliminar usuario
    public boolean eliminar(Long id) {
        try {
            if (usuarioRepositorio.existsById(id)) {
                usuarioRepositorio.deleteById(id);
                System.out.println("🗑️ USUARIO ELIMINADO - ID: " + id);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("❌ ERROR AL ELIMINAR USUARIO: " + e.getMessage());
            return false;
        }
    }
    
    // ================= FUNCIONES DE BÚSQUEDA =================
    
    // Buscar por usuario
    public Optional<Usuario> buscarPorUsuario(String usuario) {
        try {
            return usuarioRepositorio.findByUsuario(usuario);
        } catch (Exception e) {
            System.err.println("❌ ERROR AL BUSCAR POR USUARIO: " + e.getMessage());
            return Optional.empty();
        }
    }
    
    // Búsqueda con filtro
    public List<Usuario> buscar(String busqueda) {
        try {
            if (busqueda == null || busqueda.trim().isEmpty()) {
                return listarTodos();
            }
            
            System.out.println("🔍 BUSCANDO: " + busqueda);
            List<Usuario> usuarios = usuarioRepositorio.buscarUsuarios(busqueda.trim());
            System.out.println("📊 RESULTADOS DE BÚSQUEDA: " + usuarios.size());
            
            return usuarios;
            
        } catch (Exception e) {
            System.err.println("❌ ERROR EN BÚSQUEDA: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }
    
    // Listar por rango
    public List<Usuario> listarPorRango(String rango) {
        try {
            System.out.println("📂 FILTRANDO POR RANGO: " + rango);
            List<Usuario> usuarios = usuarioRepositorio.findByRango(rango);
            System.out.println("📊 USUARIOS CON RANGO '" + rango + "': " + usuarios.size());
            return usuarios;
        } catch (Exception e) {
            System.err.println("❌ ERROR AL FILTRAR POR RANGO: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }
    
    // ================= FUNCIONES DE LOGIN =================
    
    // Autenticar usuario para login
    public Usuario autenticar(String usuario, String password) {
        try {
            Optional<Usuario> usuarioEncontrado = usuarioRepositorio.autenticar(usuario, password);
            return usuarioEncontrado.orElse(null);
        } catch (Exception e) {
            System.err.println("❌ ERROR EN AUTENTICACIÓN: " + e.getMessage());
            return null;
        }
    }
    
    // Método para el LoginControlador (mantener compatibilidad)
    public Usuario autenticarUsuario(String usuario, String password) {
        return autenticar(usuario, password);
    }
    
    // Verificar credenciales
    public boolean verificarCredenciales(String usuario, String password) {
        return autenticar(usuario, password) != null;
    }
    
    // ================= FUNCIONES DE VALIDACIÓN =================
    
    // Verificar si existe usuario
    public boolean existeUsuario(String usuario) {
        try {
            boolean existe = usuarioRepositorio.existsByUsuario(usuario);
            System.out.println("🔍 VERIFICANDO EXISTENCIA DE USUARIO '" + usuario + "': " + existe);
            return existe;
        } catch (Exception e) {
            System.err.println("❌ ERROR AL VERIFICAR EXISTENCIA DE USUARIO: " + e.getMessage());
            return false;
        }
    }
    
    // Validar datos del usuario
    private void validarUsuario(Usuario usuario) throws RuntimeException {
        if (usuario.getUsuario() == null || usuario.getUsuario().trim().isEmpty()) {
            throw new RuntimeException("El nombre de usuario es obligatorio");
        }
        
        if (usuario.getUsuario().length() < 3) {
            throw new RuntimeException("El nombre de usuario debe tener al menos 3 caracteres");
        }
        
        if (usuario.getUsuario().length() > 100) {
            throw new RuntimeException("El nombre de usuario no puede exceder 100 caracteres");
        }
        
        if (!usuario.getUsuario().matches("^[a-zA-Z0-9.]+$")) {
            throw new RuntimeException("El nombre de usuario solo puede contener letras, números y puntos");
        }
        
        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
            throw new RuntimeException("La contraseña es obligatoria");
        }
        
        if (usuario.getPassword().length() < 3) {
            throw new RuntimeException("La contraseña debe tener al menos 3 caracteres");
        }
        
        if (usuario.getRango() == null || usuario.getRango().trim().isEmpty()) {
            throw new RuntimeException("El rango es obligatorio");
        }
    }
    
    // ================= FUNCIONES ESTADÍSTICAS =================
    
    // Contar total de usuarios
    public Long contarTotal() {
        try {
            long total = usuarioRepositorio.count();
            System.out.println("📊 TOTAL DE USUARIOS EN BD: " + total);
            return total;
        } catch (Exception e) {
            System.err.println("❌ ERROR AL CONTAR USUARIOS: " + e.getMessage());
            return 0L;
        }
    }
    
    // Contar usuarios por rango
    public Long contarPorRango(String rango) {
        try {
            return usuarioRepositorio.countByRango(rango);
        } catch (Exception e) {
            System.err.println("❌ ERROR AL CONTAR POR RANGO: " + e.getMessage());
            return 0L;
        }
    }
    
    // Obtener rangos únicos
    public List<String> obtenerRangos() {
        try {
            List<String> rangos = usuarioRepositorio.findRangosUnicos();
            System.out.println("📊 RANGOS ÚNICOS: " + rangos);
            return rangos;
        } catch (Exception e) {
            System.err.println("❌ ERROR AL OBTENER RANGOS: " + e.getMessage());
            // Retornar rangos predeterminados
            return java.util.Arrays.asList("Administrador", "Supervisor", "Empleado", "Cajero");
        }
    }
    
    // Obtener usuarios recientes
    public List<Usuario> obtenerUsuariosRecientes(int limit) {
        try {
            List<Usuario> todos = usuarioRepositorio.findAllOrderByIdDesc();
            return todos.stream()
                    .limit(limit)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("❌ ERROR AL OBTENER USUARIOS RECIENTES: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }
    
    // ================= FUNCIONES ADICIONALES =================
    
    // Cambiar contraseña
    public boolean cambiarPassword(Long idusuario, String nuevaPassword) {
        try {
            Optional<Usuario> usuario = buscarPorId(idusuario);
            if (usuario.isPresent()) {
                Usuario u = usuario.get();
                u.setPassword(nuevaPassword);
                guardar(u);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("❌ ERROR AL CAMBIAR CONTRASEÑA: " + e.getMessage());
            return false;
        }
    }
    
    // Verificar si el usuario puede ser eliminado
    public boolean puedeEliminar(Long idusuario) {
        try {
            Optional<Usuario> usuario = buscarPorId(idusuario);
            if (usuario.isPresent() && "Administrador".equals(usuario.get().getRango())) {
                long adminCount = contarPorRango("Administrador");
                return adminCount > 1; // Debe haber al menos 2 admins para eliminar uno
            }
            return true;
        } catch (Exception e) {
            System.err.println("❌ ERROR AL VERIFICAR SI PUEDE ELIMINAR: " + e.getMessage());
            return false;
        }
    }
    
    // ================= MÉTODOS PARA ESTADÍSTICAS DEL DASHBOARD =================
    
    // Contar administradores
    public Long contarAdministradores() {
        return contarPorRango("Administrador");
    }
    
    // Contar empleados
    public Long contarEmpleados() {
        return contarPorRango("Empleado");
    }
    
    // Contar supervisores
    public Long contarSupervisores() {
        return contarPorRango("Supervisor");
    }
    
    // Contar cajeros
    public Long contarCajeros() {
        return contarPorRango("Cajero");
    }
}