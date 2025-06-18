package com.proyecto.jpa.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_categoria")
public class TblCategoria {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idcategoria;
	private String nomcategoria;
	
	//constructor con parametros
	public TblCategoria(int idcategoria, String nomcategoria) {
		super();
		this.idcategoria = idcategoria;
		this.nomcategoria = nomcategoria;
	}
	
	//constructor vacio
	public TblCategoria() {
		
	
	}
	
	//getters y setters
	public int getIdcategoria() {
		return idcategoria;
	}
	public void setIdcategoria(int idcategoria) {
		this.idcategoria = idcategoria;
	}
	public String getNomcategoria() {
		return nomcategoria;
	}
	public void setNomcategoria(String nomcategoria) {
		this.nomcategoria = nomcategoria;
	}
	
	@Override
    public String toString() {
        return nomcategoria;
    }

}
