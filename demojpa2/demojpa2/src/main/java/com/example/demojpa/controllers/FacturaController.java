package com.example.demojpa.controllers;


import com.example.demojpa.models.Factura;
import com.example.demojpa.models.FacturaDTO;
import com.example.demojpa.services.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/facturas")
public class FacturaController {
    @Autowired
    private FacturaService facturaService;

    @GetMapping("get")
    public List<FacturaDTO> getFactura() {
        return facturaService.listarFacturas();
    }

    @PostMapping("agregar")
    public ResponseEntity<String> agregar(@RequestBody Factura factura){
        return facturaService.agregarFactura(factura);
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return facturaService.eliminarFactura(id);
    }
}
