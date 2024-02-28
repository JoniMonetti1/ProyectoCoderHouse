package com.example.demojpa.services;

import com.example.demojpa.models.Cliente;
import com.example.demojpa.models.ClienteDTO;
import com.example.demojpa.repository.ClienteRepository;
import com.example.demojpa.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FacturaRepository repositoryFactura;

    public List<ClienteDTO> getAllClientes() {
        List<ClienteDTO> clienteDTOS = new ArrayList<>();
        for (Cliente cliente : clienteRepository.findAll()){
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setNombre(cliente.getName());
            clienteDTO.setApellido(cliente.getLastname());
            clienteDTO.setDni(cliente.getDocNumber());
            clienteDTOS.add(clienteDTO);
        }
        return clienteDTOS;
    }

    public ResponseEntity<String> createCliente(Cliente cliente) {
        if (cliente.getName() != null || cliente.getLastname() != null || cliente.getDocNumber() != null) {
            try {
                this.clienteRepository.save(cliente);
                return ResponseEntity.status(200).body("cod. 200: Operacion realizada con exito");
            } catch (Exception e) {
                return ResponseEntity.status(409).body("cod. 409: La operacion no pudo ser llevada a cabo");
            }
        } else return ResponseEntity.status(409).body("cod. 409: La operacion no pudo ser llevada a caboi");
    }

    public ResponseEntity<String> updateCliente(Long id, ClienteDTO cliente) {
        try {
            Optional<Cliente> db = clienteRepository.findById(id);
            if (db.isPresent()) {
                Cliente updateCliente = db.get();
                updateCliente.setDocNumber(cliente.getDni());
                updateCliente.setName(cliente.getNombre());
                updateCliente.setLastname(cliente.getApellido());
                clienteRepository.save(updateCliente);
                return ResponseEntity.status(HttpStatus.OK).body("cod.200: Cliente actualizado correctamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("cod.409: No se encontró el cliente con el ID proporcionado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor al actualizar el cliente");
        }
    }


    public void clientInitializer(){
        clienteRepository.save(new Cliente("Jonathan", "Monetti", "45244920"));
        clienteRepository.save(new Cliente("Alcides", "Monetti", "18078993"));
        clienteRepository.save(new Cliente("Malena", "Maidana", "45244921"));
    }
}
