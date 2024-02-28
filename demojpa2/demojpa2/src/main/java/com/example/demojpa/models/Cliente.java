package com.example.demojpa.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "clientes")
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "NOMBRE")
    private String name;

    @Column(name = "APELLIDO")
    private String lastname;

    @Column(name = "DNI")
    private String docNumber;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Factura> facturas = new ArrayList<>();

    public Cliente(String name, String lastname, String docNumber){
        this.name = name;
        this.lastname = lastname;
        this.docNumber = docNumber;
    }
}
