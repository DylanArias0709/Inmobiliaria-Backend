package com.g2inmobiliaria.app.Controllers;

import com.g2inmobiliaria.app.Request.UserRegistrationRequest;
import com.g2inmobiliaria.app.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody UserRegistrationRequest request) {
        // Aquí puedes validar y procesar los datos de registro
        userService.registrarUsuario(request.getUserName(), request.getPassword(), request.getActivationToken(), request.getVerificationToken(), request.getIdRole());
        return "Usuario registrado exitosamente";
    }
}
