package com.example.demojpa.services;

import com.example.demojpa.models.Producto;
import com.example.demojpa.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public Producto createProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto updateProducto(Long id, Producto producto) {
        Producto existingProducto = productoRepository.findById(id).orElse(null);
        if (existingProducto != null) {
            existingProducto.setDescription(producto.getDescription());
            existingProducto.setCode(producto.getCode());
            existingProducto.setStock(producto.getStock());
            existingProducto.setPrice(producto.getPrice());
            return productoRepository.save(existingProducto);
        }
        return null; // Producto no encontrado
    }

    public boolean deleteProducto(Long id) {
        Producto existingProducto = productoRepository.findById(id).orElse(null);
        if (existingProducto != null) {
            productoRepository.delete(existingProducto);
            return true;
        }
        return false; // Producto no encontrado
    }
}
