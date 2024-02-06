package com.example.demojpa.services;

import com.example.demojpa.models.Cliente;
import com.example.demojpa.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente updateCliente(Long id, Cliente cliente) {
        Cliente existingCliente = clienteRepository.findById(id).orElse(null);
        if (existingCliente != null) {
            existingCliente.setName(cliente.getName());
            existingCliente.setLastname(cliente.getLastname());
            existingCliente.setDocNumber(cliente.getDocNumber());
            return clienteRepository.save(existingCliente);
        }
        return null; // Cliente no encontrado
    }

    public boolean deleteCliente(Long id) {
        Cliente existingCliente = clienteRepository.findById(id).orElse(null);
        if (existingCliente != null) {
            clienteRepository.delete(existingCliente);
            return true;
        }
        return false; // Cliente no encontrado
    }
}
