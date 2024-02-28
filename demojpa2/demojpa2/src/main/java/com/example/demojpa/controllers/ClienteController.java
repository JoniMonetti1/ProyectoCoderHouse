package com.example.demojpa.controllers;

import com.example.demojpa.models.Cliente;
import com.example.demojpa.models.ClienteDTO;
import com.example.demojpa.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/clientes") // Agrupamos las URL relacionadas a clientes bajo esta ruta base
public class ClienteController {

    @Autowired
    private ClienteService clienteService;


    @GetMapping("get")
    public List<ClienteDTO> getClientes() {
        return clienteService.getAllClientes();
    }

    @PostMapping("agregar")
    public ResponseEntity<String> createCliente(@RequestBody Cliente cliente) {
        return clienteService.createCliente(cliente);
    }

    @PutMapping("modificar/{id}")
    public ResponseEntity<String> updateCliente(@PathVariable Long id, @RequestBody ClienteDTO cliente) {
        return clienteService.updateCliente(id, cliente);
    }
}
