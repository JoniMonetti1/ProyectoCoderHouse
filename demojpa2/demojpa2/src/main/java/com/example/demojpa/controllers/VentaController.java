package com.example.demojpa.controllers;

import com.example.demojpa.models.Venta;
import com.example.demojpa.services.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas") // Agrupamos las URL relacionadas a ventas bajo esta ruta base
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public List<Venta> getVentas() {
        return ventaService.getAllVentas();
    }

    @PostMapping("/alta")
    public Venta createVenta(@RequestBody Venta venta) {
        return ventaService.createVenta(venta);
    }

    @PutMapping("/modificar/{id}")
    public Venta updateVenta(@PathVariable Long id, @RequestBody Venta venta) {
        return ventaService.updateVenta(id, venta);
    }

    @DeleteMapping("/baja/{id}")
    public boolean deleteVenta(@PathVariable Long id) {
        return ventaService.deleteVenta(id);
    }
}
