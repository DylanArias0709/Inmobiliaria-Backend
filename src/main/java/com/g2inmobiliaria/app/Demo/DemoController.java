package com.g2inmobiliaria.app.Demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //Hacer esta entidad un API RESTFULL.
@RequestMapping("/api/v1/demo-controller") //URL a utilizar api para utilizar la autentificacion.
public class DemoController {

    @GetMapping
    public ResponseEntity<String> sayHello(){

        return ResponseEntity.ok("Hola desde el DemoController-Endpoint Seguro");
    }
}
