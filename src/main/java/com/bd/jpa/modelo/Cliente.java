package com.bd.jpa.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_cliente")
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcliente")
    private Long idcliente;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;
    
    @Column(name = "telefono", length = 20)
    private String telefono;
    
    @Column(name = "email", length = 150)
    private String email;
    
    @Column(name = "direccion", length = 255)
    private String direccion;
    
    @Column(name = "dni", length = 20)
    private String dni;
    
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
    
    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    // Constructores
    public Cliente() {
        this.fechaRegistro = LocalDateTime.now();
        this.activo = true;
    }
    
    public Cliente(String nombre, String apellido, String telefono, String email) {
        this();
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
    }
    
    // Métodos de ciclo de vida
    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    // Getters y Setters
    public Long getIdcliente() {
        return idcliente;
    }
    
    public void setIdcliente(Long idcliente) {
        this.idcliente = idcliente;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String getDni() {
        return dni;
    }
    
    public void setDni(String dni) {
        this.dni = dni;
    }
    
    public Boolean getActivo() {
        return activo;
    }
    
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }
    
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
    
    // Método para obtener nombre completo
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
    
    @Override
    public String toString() {
        return "Cliente{" +
                "idcliente=" + idcliente +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", activo=" + activo +
                '}';
    }
}
