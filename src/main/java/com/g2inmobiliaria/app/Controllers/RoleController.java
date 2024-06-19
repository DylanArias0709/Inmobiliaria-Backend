package com.g2inmobiliaria.app.Controllers;

import com.g2inmobiliaria.app.Entities.Role;
import com.g2inmobiliaria.app.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/listarRoles")
    public List<Role> listarEntidades() {
        return roleService.listarRoles();
    }
}
