package com.g2inmobiliaria.app.Controllers;

import com.g2inmobiliaria.app.Entities.Role;
import com.g2inmobiliaria.app.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/listarRoles")
    public String listarEntidades(Model model) {
        List<Role> listaRoles = roleService.listarRoles();
        model.addAttribute("roles", listaRoles);
        return "roles/roles_admin";
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
    public ResponseEntity<?> borradoLogicoRole(@RequestParam("role") int id){
        return ResponseEntity.ok().body(roleService.borradoLogicoRole(id));
    }
}
