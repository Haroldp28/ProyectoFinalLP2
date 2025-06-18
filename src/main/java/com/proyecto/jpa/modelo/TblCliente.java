package com.proyecto.jpa.modelo;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class TblCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idcliente;
    private String nomcliente;
    private String apecliente;
    private String dnicliente;
    private String telcliente;

    // Constructor con parámetros
    public TblCliente(int idcliente, String nomcliente, String apecliente, String dnicliente, String telcliente) {
        this.idcliente = idcliente;
        this.nomcliente = nomcliente;
        this.apecliente = apecliente;
        this.dnicliente = dnicliente;
        this.telcliente = telcliente;
    }
    // Constructor vacío
    public TblCliente() {
    }
    // Getters y Setters
    public int getIdcliente() {
        return idcliente;
    }
    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }
    public String getNomcliente() {
        return nomcliente;
    }
    public void setNomcliente(String nomcliente) {
        this.nomcliente = nomcliente;
    }
    public String getApecliente() {
        return apecliente;
    }
    public void setApecliente(String apecliente) {
        this.apecliente = apecliente;
    }
    public String getDnicliente() {
        return dnicliente;
    }
    public void setDnicliente(String dnicliente) {
        this.dnicliente = dnicliente;
    }
    public String getTelcliente() {
        return telcliente;
    }
    public void setTelcliente(String telcliente) {
        this.telcliente = telcliente;
    }

}
