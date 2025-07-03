package com.bd.jpa.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tbl_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Long idusuario;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 100, message = "El usuario debe tener entre 3 y 100 caracteres")
    @Column(name = "usuario", nullable = false, length = 100, unique = true)
    private String usuario;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 3, message = "La contraseña debe tener al menos 3 caracteres")
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @NotBlank(message = "El rango es obligatorio")
    @Column(name = "rango", nullable = false, length = 50)
    private String rango;

    // Constructores
    public Usuario() {
        // Constructor vacío requerido por JPA
    }

    public Usuario(String usuario, String password, String rango) {
        this.usuario = usuario;
        this.password = password;
        this.rango = rango;
    }

    // Getters y Setters
    public Long getIdusuario() { 
        return idusuario; 
    }
    
    public void setIdusuario(Long idusuario) { 
        this.idusuario = idusuario; 
    }

    public String getUsuario() { 
        return usuario; 
    }
    
    public void setUsuario(String usuario) { 
        this.usuario = usuario != null ? usuario.trim() : null; 
    }

    public String getPassword() { 
        return password; 
    }
    
    public void setPassword(String password) { 
        this.password = password; 
    }

    public String getRango() { 
        return rango; 
    }
    
    public void setRango(String rango) { 
        this.rango = rango; 
    }

    // Métodos de utilidad
    @Override
    public String toString() {
        return "Usuario{" +
                "idusuario=" + idusuario +
                ", usuario='" + usuario + '\'' +
                ", rango='" + rango + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario1 = (Usuario) o;
        return idusuario != null && idusuario.equals(usuario1.idusuario);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // Método para verificar si es un nuevo usuario
    public boolean esNuevo() {
        return this.idusuario == null;
    }
}