package com.proyecto.jpa.modelo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
@Table(name="tbl_producto")
public class TblProducto {
	
	//declaramos
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idproducto;
	private String nomproducto;
	private double precproducto;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso=ISO.DATE)
	private Date fechaelaproducto;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso=ISO.DATE)
	private Date fechavenproducto;
	@ManyToOne
    @JoinColumn(name = "idcategoria", referencedColumnName = "idcategoria")
	private TblCategoria categoria;
	
	//constructor con parametros
	public TblProducto(int idproducto, String nomproducto, double precproducto, Date fechaelaproducto,
			Date fechavenproducto, TblCategoria categoria) {
		super();
		this.idproducto = idproducto;
		this.nomproducto = nomproducto;
		this.precproducto = precproducto;
		this.fechaelaproducto = fechaelaproducto;
		this.fechavenproducto = fechavenproducto;
		this.categoria = categoria;
	}
	
	//constructor vacio
	public TblProducto() {
		
		
	}
	
	//getters y setters
	
	public int getIdproducto() {
		return idproducto;
	}
	public void setIdproducto(int idproducto) {
		this.idproducto = idproducto;
	}
	public String getNomproducto() {
		return nomproducto;
	}
	public void setNomproducto(String nomproducto) {
		this.nomproducto = nomproducto;
	}
	public double getPrecproducto() {
		return precproducto;
	}
	public void setPrecproducto(double precproducto) {
		this.precproducto = precproducto;
	}
	public Date getFechaelaproducto() {
		return fechaelaproducto;
	}
	public void setFechaelaproducto(Date fechaelaproducto) {
		this.fechaelaproducto = fechaelaproducto;
	}
	public Date getFechavenproducto() {
		return fechavenproducto;
	}
	public void setFechavenproducto(Date fechavenproducto) {
		this.fechavenproducto = fechavenproducto;
	}
	public TblCategoria getCategoria() {
		return categoria;
	}
	public void setCategoria(TblCategoria categoria) {
		this.categoria = categoria;
	}
	
	
}
