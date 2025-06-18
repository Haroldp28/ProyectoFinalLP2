package com.proyecto.jpa.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.jpa.modelo.TblCliente;
import com.proyecto.jpa.repositorio.IClienteRepositorio;

@Service
public class ClienteServicioImp implements IClienteServicio {

    @Autowired
    private  IClienteRepositorio iCliente;

    @Override
    public void RegistrarCliente(TblCliente cliente) {
        iCliente.save(cliente);
    }

    @Override
    public void EliminarCliente(TblCliente cliente) {
       iCliente.delete(cliente);
    }

    @Override
    public List<TblCliente> ListarClientes() {
        return (List<TblCliente>) iCliente.findAll();
    }

    @Override
    public TblCliente BuscarCliente(Integer id) {
        return iCliente.findById(id).orElse(null);
    }
}
