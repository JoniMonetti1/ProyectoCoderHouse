package com.example.demojpa.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "facturas")
public class Factura implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Cliente cliente;

    @Column(name = "FECHA")
    private Date fecha;

    @Column(name = "TOTAL")
    private double total;

    @Column(name = "CANTIDAD_PRODUCTOS")
    private int cantidadProductos;

    @OneToMany(mappedBy = "factura", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<DetalleFactura> detalleFactura;

    public Factura(Cliente cliente, double total) {
        this.cliente = cliente;
        this.total = total;
        this.fecha = new Date();
    }
}
