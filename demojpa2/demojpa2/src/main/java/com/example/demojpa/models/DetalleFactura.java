package com.example.demojpa.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DetalleFactura implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "CANTIDAD_PRODUCTO")
    private int cantidad;

    @Column(name = "PRECIO_PRODUCTO")
    private double precioProducto;

    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "FK_FACURA", nullable = false, updatable = false)
    private Factura factura;

    public DetalleFactura(int cantidad, double precioProducto, Factura factura) {
        this.cantidad = cantidad;
        this.precioProducto = precioProducto;
        this.factura = factura;
    }
}
