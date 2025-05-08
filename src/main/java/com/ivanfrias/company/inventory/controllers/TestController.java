package com.ivanfrias.company.inventory.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TestController {
    @GetMapping("/test")
    public ResponseEntity<Object> test(){
        // Esto es simplemente sirve para testear que el microservicio funciona correctamente protegiendo la API
        return ResponseEntity.ok("Test ejecutado con éxito");
    }
}
