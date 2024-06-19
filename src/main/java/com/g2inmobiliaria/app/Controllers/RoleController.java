package com.g2inmobiliaria.app.Controllers;

import com.g2inmobiliaria.app.Entities.Role;
import com.g2inmobiliaria.app.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/registrarRole")
    public String registrarRole(@RequestBody Role role) {
        return roleService.registrarRole(role);
    }

    @PutMapping("/actualzarRole")
    public String actualizarRole(@RequestBody Role role) {
        return roleService.actualizarRole(role);
    }

    @DeleteMapping("/eliminarRole")
    public String borradoLogicoRole(@RequestParam("role") int id){
        return roleService.borradoLogicoRole(id);
    }
}
