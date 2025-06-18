package com.proyecto.jpa.servicio;

import java.util.List;

import com.proyecto.jpa.modelo.TblCliente;

public interface IClienteServicio {
    void RegistrarCliente(TblCliente cliente);
    void EliminarCliente(TblCliente cliente);
    List<TblCliente> ListarClientes();
    TblCliente BuscarCliente(Integer id);
} 