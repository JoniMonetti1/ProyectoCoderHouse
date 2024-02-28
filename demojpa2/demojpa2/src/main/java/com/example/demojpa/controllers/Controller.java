package com.example.demojpa.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("status")
    public ResponseEntity<String> index() {
        return ResponseEntity.status(200).body("200:Conexion establecida");
    }

}
