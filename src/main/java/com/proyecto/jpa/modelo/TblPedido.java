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
@Table(name = "tbl_pedido")
public class TblPedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idpedido;
	private int idproducto;
	private int idcliente;
	private int idvendedor;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso=ISO.DATE)
	private Date fechapedido;
	private String estapedido;
	private double precio;
	private int cantidad;
	private double total;

	@ManyToOne
    @JoinColumn(name = "producto", referencedColumnName = "idproducto")
	private TblProducto producto;
	
	 @JoinColumn(name = "cliente", referencedColumnName = "idcliente")
	private TblCliente cliente;

	 @JoinColumn(name = "vendedor", referencedColumnName = "idvendedor")
	private TblVendedor vendedor;


	//constructor con parametros
	public TblPedido(int idpedido, int idproducto, int idcliente, int idvendedor, Date fechapedido,String estapedido,
	double precio, int cantidad, double total, TblProducto producto, TblCliente cliente, TblVendedor vendedor) {
		super();
		this.idpedido = idpedido;
		this.idproducto = idproducto;
		this.idcliente = idcliente;
		this.idvendedor = idvendedor;
		this.fechapedido = fechapedido;
		this.estapedido = estapedido;
		this.precio = precio;
		this.cantidad = cantidad;
		this.total = total;
		this.producto = producto;
		this.cliente = cliente;
		this.vendedor = vendedor;
	}
	
	//constructor vacio
	public TblPedido() {
		
	
	}
	
	//getters y setters
	public int getIdpedido() {
		return idpedido;
	}
	public void setIdpedido(int idpedido) {
		this.idpedido = idpedido;
	}

	public int getIdproducto() {
		return idpedido;
	}
	public void setIdproducto(int idproducto) {
		this.idproducto = idproducto;
	}
	public int getIdcliente() {
		return idcliente;
	}
	public void setIdcliente(int idcliente) {
		this.idcliente = idcliente;
	}
	public int getIdvendedor() {
		return idvendedor;
	}
	public void setIdvendedor(int idvendedor) {
		this.idvendedor = idvendedor;
	}
	public Date getFechapedido() {
		return fechapedido;
	}
	public void setFechapedido(Date fechapedido) {
		this.fechapedido = fechapedido;
	}
	public String getEstapedido() {
		return estapedido;
	}
	public void setEstapedido(String estapedido) {
		this.estapedido = estapedido;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	public TblProducto getProducto() {
		return producto;
	}
	public void setProducto(TblProducto producto) {
		this.producto = producto;
	}
	public TblCliente getCliente() {
		return cliente;
	}
	public void setCliente(TblCliente cliente) {
		this.cliente = cliente;
	}
	public TblVendedor getVendedor() {
		return vendedor;
	}
	public void setVendedor(TblVendedor vendedor) {
		this.vendedor = vendedor;
	}

}
