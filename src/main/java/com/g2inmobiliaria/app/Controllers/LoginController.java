package com.g2inmobiliaria.app.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "LoginView"; // Este es el nombre del archivo HTML sin la extensión .html
    }
}
