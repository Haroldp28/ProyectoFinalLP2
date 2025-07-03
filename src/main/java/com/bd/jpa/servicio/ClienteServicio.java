
package com.bd.jpa.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bd.java.repositorio.ClienteRepositorio;
import com.bd.jpa.modelo.Cliente;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteServicio {
    
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    
    // Guardar cliente
    public Cliente guardar(Cliente cliente) {
        return clienteRepositorio.save(cliente);
    }
    
    // Obtener todos los clientes
    public List<Cliente> obtenerTodos() {
        return clienteRepositorio.findAll();
    }
    
    // Obtener todos ordenados por nombre
    public List<Cliente> obtenerTodosOrdenados() {
        return clienteRepositorio.findAllOrderByNombre();
    }
    
    // Obtener cliente por ID
    public Optional<Cliente> obtenerPorId(Long id) {
        return clienteRepositorio.findById(id);
    }
    
    // Obtener cliente por DNI
    public Optional<Cliente> obtenerPorDni(String dni) {
        return clienteRepositorio.findByDni(dni);
    }
    
    // Obtener cliente por email
    public Optional<Cliente> obtenerPorEmail(String email) {
        return clienteRepositorio.findByEmail(email);
    }
    
    // Obtener clientes activos
    public List<Cliente> obtenerActivos() {
        return clienteRepositorio.findByActivoTrue();
    }
    
    // Obtener clientes activos ordenados
    public List<Cliente> obtenerActivosOrdenados() {
        return clienteRepositorio.findActivosOrderByNombre();
    }
    
    // Buscar clientes por nombre
    public List<Cliente> buscarPorNombre(String nombre) {
        return clienteRepositorio.findByNombreContaining(nombre);
    }
    
    // Buscar clientes por apellido
    public List<Cliente> buscarPorApellido(String apellido) {
        return clienteRepositorio.findByApellidoContaining(apellido);
    }
    
    // Buscar clientes por nombre completo
    public List<Cliente> buscarPorNombreCompleto(String termino) {
        return clienteRepositorio.findByNombreCompletoContaining(termino);
    }
    
    // Buscar por DNI, email o teléfono
    public List<Cliente> buscarPorDniEmailTelefono(String valor) {
        return clienteRepositorio.findByDniEmailTelefono(valor);
    }
    
    // Verificar si existe DNI
    public boolean existeDni(String dni) {
        return clienteRepositorio.existsByDni(dni);
    }
    
    // Verificar si existe email
    public boolean existeEmail(String email) {
        return clienteRepositorio.existsByEmail(email);
    }
    
    // Verificar si existe cliente
    public boolean existe(Long id) {
        return clienteRepositorio.existsById(id);
    }
    
    // Actualizar cliente
    public Cliente actualizar(Cliente cliente) {
        if (cliente.getIdcliente() != null && existe(cliente.getIdcliente())) {
            return clienteRepositorio.save(cliente);
        }
        throw new RuntimeException("Cliente no encontrado con ID: " + cliente.getIdcliente());
    }
    
    // Activar cliente
    public void activar(Long id) {
        Optional<Cliente> clienteOpt = obtenerPorId(id);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            cliente.setActivo(true);
            guardar(cliente);
        } else {
            throw new RuntimeException("Cliente no encontrado con ID: " + id);
        }
    }
    
    // Desactivar cliente (soft delete)
    public void desactivar(Long id) {
        Optional<Cliente> clienteOpt = obtenerPorId(id);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            cliente.setActivo(false);
            guardar(cliente);
        } else {
            throw new RuntimeException("Cliente no encontrado con ID: " + id);
        }
    }
    
    // Eliminar cliente permanentemente
    public void eliminar(Long id) {
        if (existe(id)) {
            clienteRepositorio.deleteById(id);
        } else {
            throw new RuntimeException("Cliente no encontrado con ID: " + id);
        }
    }
    
    // Registrar nuevo cliente
    public Cliente registrarCliente(String nombre, String apellido, String telefono, String email, String direccion, String dni) {
        
        // Verificar si ya existe el DNI
        if (dni != null && !dni.trim().isEmpty() && existeDni(dni)) {
            throw new RuntimeException("Ya existe un cliente con este DNI");
        }
        
        // Verificar si ya existe el email
        if (email != null && !email.trim().isEmpty() && existeEmail(email)) {
            throw new RuntimeException("Ya existe un cliente con este email");
        }
        
        // Crear nuevo cliente
        Cliente nuevoCliente = new Cliente(nombre, apellido, telefono, email);
        nuevoCliente.setDireccion(direccion);
        nuevoCliente.setDni(dni);
        
        return guardar(nuevoCliente);
    }
    
    // Contar total de clientes
    public long contarTotal() {
        return clienteRepositorio.count();
    }
    
    // Contar clientes activos
    public long contarActivos() {
        return obtenerActivos().size();
    }
    
    // Contar clientes inactivos
    public long contarInactivos() {
        return clienteRepositorio.findByActivoFalse().size();
    }
    
    // Obtener clientes registrados en un período
    public List<Cliente> obtenerPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return clienteRepositorio.findByFechaRegistroBetween(fechaInicio, fechaFin);
    }
}
