package com.example.demojpa.services;

import com.example.demojpa.models.Producto;
import com.example.demojpa.models.ProductoDTO;
import com.example.demojpa.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<ProductoDTO> listarProductos() {
        List<ProductoDTO> productosDTO = new ArrayList<>();
        for (Producto producto : productoRepository.findAll()) {
            ProductoDTO dtoProducto = new ProductoDTO();
            dtoProducto.setCodigo(producto.getCode());
            dtoProducto.setDescripcion(producto.getDescription());
            productosDTO.add(dtoProducto);
        }
        return productosDTO;
    }

    public ResponseEntity<String> agregarProducto(Producto producto) {
        if (producto.getDescription() == null || producto.getCode() == null) {
            return ResponseEntity.status(409).body("cod. 409: No se pudo agregar el producto, por favor revise los datos proporcionados");
        } else {
            try {
                productoRepository.save(producto);
                return ResponseEntity.status(200).body("cod. 200: Producto agregado exitosamente!");
            } catch (Exception e) {
                return ResponseEntity.status(409).body("cod. 409: No se pudo agregar el producto, por favor revise los datos proporcionados.");
            }
        }
    }

    public ResponseEntity<String> actualizarProducto(Long id, Producto producto) {
        try {
            Optional<Producto> productoExistente = productoRepository.findById(id);
            if (productoExistente.isPresent()) {
                Producto productoActualizado = productoExistente.get();
                productoActualizado.setDescription(producto.getDescription());
                productoActualizado.setCode(producto.getCode());
                productoActualizado.setStock(producto.getStock());
                productoActualizado.setPrice(producto.getPrice());
                productoRepository.save(productoActualizado);
                return ResponseEntity.status(200).body("cod. 200: Producto actualizado exitosamente!");
            } else {
                return ResponseEntity.status(409).body("cod. 409: No se encontró el producto con el ID proporcionado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(409).body("cod. 409: No se pudo realizar la operación de actualización, por favor inténtelo de nuevo.");
        }
    }


    public ResponseEntity<String> actualizarStockProducto(Long id, int unidadesVendidas) {
        try {
            Optional<Producto> productoExistente = productoRepository.findById(id);
            if (productoExistente.isPresent()) {
                Producto productoActualizado = productoExistente.get();
                productoActualizado.setStock(productoActualizado.getStock() - unidadesVendidas);
                productoRepository.save(productoActualizado);
                return ResponseEntity.status(200).body("cod. 200: Stock del producto actualizado exitosamente!");
            } else {
                return ResponseEntity.status(409).body("cod. 409: No se encontró el producto con el ID proporcionado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(409).body("cod. 409: No se pudo realizar la actualización del stock, por favor inténtelo de nuevo.");
        }
    }
}
