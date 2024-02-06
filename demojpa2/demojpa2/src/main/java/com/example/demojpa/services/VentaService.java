package com.example.demojpa.services;

import com.example.demojpa.models.Venta;
import com.example.demojpa.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    public List<Venta> getAllVentas() {
        return ventaRepository.findAll();
    }

    public Venta createVenta(Venta venta) {
        return ventaRepository.save(venta);
    }

    public Venta updateVenta(Long id, Venta venta) {
        Venta existingVenta = ventaRepository.findById(id).orElse(null);
        if (existingVenta != null) {
            existingVenta.setCliente(venta.getCliente());
            existingVenta.setFecha(venta.getFecha());
            existingVenta.setProductos(venta.getProductos());
            return ventaRepository.save(existingVenta);
        }
        return null; // Venta no encontrada
    }

    public boolean deleteVenta(Long id) {
        Venta existingVenta = ventaRepository.findById(id).orElse(null);
        if (existingVenta != null) {
            ventaRepository.delete(existingVenta);
            return true;
        }
        return false; // Venta no encontrada
    }
}
