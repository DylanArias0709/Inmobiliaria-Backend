package com.g2inmobiliaria.app.Auth;

import com.g2inmobiliaria.app.Auth.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //Hacer esta entidad un API RESTFULL.
@RequestMapping("/api/v1/auth") //URL a utilizar api para utilizar la autentificacion.
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody final RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody final AuthenticationRequest  request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }


}
