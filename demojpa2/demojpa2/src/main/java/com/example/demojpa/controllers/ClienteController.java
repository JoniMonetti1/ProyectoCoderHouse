package com.example.demojpa.controllers;

import com.example.demojpa.models.Cliente;
import com.example.demojpa.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes") // Agrupamos las URL relacionadas a clientes bajo esta ruta base
public class ClienteController {

    @Autowired
    private ClienteService clienteService;


    @GetMapping
    public List<Cliente> getClientes() {
        return clienteService.getAllClientes();
    }

    @PostMapping("/alta")
    public Cliente createCliente(@RequestBody Cliente cliente) {
        return clienteService.createCliente(cliente);
    }

    @PutMapping("/modificar/{id}")
    public Cliente updateCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        return clienteService.updateCliente(id, cliente);
    }

    @DeleteMapping("/baja/{id}")
    public boolean deleteCliente(@PathVariable Long id) {
        return clienteService.deleteCliente(id);
    }
}
