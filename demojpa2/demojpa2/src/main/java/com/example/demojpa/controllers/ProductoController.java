package com.example.demojpa.controllers;

import com.example.demojpa.models.Producto;
import com.example.demojpa.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos") // Agrupamos las URL relacionadas a productos bajo esta ruta base
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Producto> getProductos() {
        return productoService.getAllProductos();
    }

    @PostMapping("/alta")
    public Producto createProducto(@RequestBody Producto producto) {
        return productoService.createProducto(producto);
    }

    @PutMapping("/modificar/{id}")
    public Producto updateProducto(@PathVariable Long id, @RequestBody Producto producto) {
        return productoService.updateProducto(id, producto);
    }

    @DeleteMapping("/baja/{id}")
    public boolean deleteProducto(@PathVariable Long id) {
        return productoService.deleteProducto(id);
    }
}
