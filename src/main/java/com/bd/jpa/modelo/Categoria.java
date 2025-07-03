package com.bd.jpa.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_categoria")
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcategoria")
    private Long idcategoria;
    
    @Column(name = "nomcategoria", nullable = false, length = 100)  // ← Cambiar aquí
    private String nombre;  // ← Mantener este nombre en Java
    
    @Column(name = "descripcion", length = 255)
    private String descripcion;
    
    @Column(name = "icono", length = 50)
    private String icono;
    
    // Constructores
    public Categoria() {}
    
    public Categoria(String nombre, String descripcion, String icono) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.icono = icono;
    }
    
    // Getters y Setters (mantener igual)
    public Long getIdcategoria() { return idcategoria; }
    public void setIdcategoria(Long idcategoria) { this.idcategoria = idcategoria; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public String getIcono() { return icono; }
    public void setIcono(String icono) { this.icono = icono; }
}