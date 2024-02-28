package com.example.demojpa.controllers;

import com.example.demojpa.models.Producto;
import com.example.demojpa.models.ProductoDTO;
import com.example.demojpa.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RestController
@RequestMapping("api/productos") // Agrupamos las URL relacionadas a productos bajo esta ruta base
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("get")
    public List<ProductoDTO> getProductos() {
        return productoService.listarProductos();
    }

    @PostMapping("/alta")
    public ResponseEntity<String> createProducto(@RequestBody Producto producto) {
        return productoService.agregarProducto(producto);
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<String> updateProducto(@PathVariable Long id, @RequestBody Producto producto) {
        return productoService.actualizarProducto(id, producto);
    }
}
