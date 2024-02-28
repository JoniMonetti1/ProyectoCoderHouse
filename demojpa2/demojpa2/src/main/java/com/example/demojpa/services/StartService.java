package com.example.demojpa.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartService {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private FacturaService facturaService;

    @EventListener(ApplicationReadyEvent.class)
    public void startService() {
        clienteService.clientInitializer();
        facturaService.cargarDatosFactura();
    }
}
