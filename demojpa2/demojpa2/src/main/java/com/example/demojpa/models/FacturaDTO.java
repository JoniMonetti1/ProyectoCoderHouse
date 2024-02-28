package com.example.demojpa.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FacturaDTO {

    private Long id;
    private ClienteDTO cliente;
    private Date fecha;
    private double total;
    private List<DetalleFacturaDTO> detalleFacturaDTO;
    private int productosVendidos;


}
