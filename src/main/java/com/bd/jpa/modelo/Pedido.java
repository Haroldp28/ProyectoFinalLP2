package com.bd.jpa.modelo;

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
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idpedido;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso=ISO.DATE)
	private Date fechapedido;
	private String estapedido;
	private double precio;
	private int cantidad;
	private double total;

	@ManyToOne
    @JoinColumn(name = "producto", referencedColumnName = "idproducto")
	private Producto producto;
	
	@ManyToOne
	 @JoinColumn(name = "cliente", referencedColumnName = "idcliente")
	private Cliente cliente;

	@ManyToOne
	 @JoinColumn(name = "vendedor", referencedColumnName = "idvendedor")
	private Vendedor vendedor;


	//constructor con parametros
	public Pedido(Long idpedido, Date fechapedido,String estapedido,
	double precio, int cantidad, double total, Producto producto, Cliente cliente, Vendedor vendedor) {
		super();
		this.idpedido = idpedido;
		
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
	public Pedido() {
		
	
	}
	
	//getters y setters
	public Long getIdpedido() {
		return idpedido;
	}
	public void setIdpedido(Long idpedido) {
		this.idpedido = idpedido;
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
	
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Vendedor getVendedor() {
		return vendedor;
	}
	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

}
