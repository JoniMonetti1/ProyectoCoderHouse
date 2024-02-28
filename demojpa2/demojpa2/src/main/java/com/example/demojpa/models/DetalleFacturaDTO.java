package com.example.demojpa.models;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleFacturaDTO {
    private int cantidad;
    private ProductoDTO productoDTO;
    private double precioProducto;
}
