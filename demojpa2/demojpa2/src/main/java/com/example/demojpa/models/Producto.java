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
@Table(name = "productos")
public class Producto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String description;

    @Column
    private String code;

    @Column
    private int stock;

    @Column
    private double price;


    public Producto(String description, String code, int stock, double price){
        this.description = description;
        this.code = code;
        this.stock = stock;
        this.price = price;
    }
}