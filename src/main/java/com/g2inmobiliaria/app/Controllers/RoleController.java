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

    @PostMapping("/actualizarRole")
    public ResponseEntity<?> actualizarRole(@RequestBody Role role) {
        return ResponseEntity.ok().body(roleService.actualizarRole(role));
    }

    @DeleteMapping("/eliminarRole")
    public ResponseEntity<?> borradoLogicoRole(@RequestParam("role") int id){
        return ResponseEntity.ok().body(roleService.borradoLogicoRole(id));
    }

    @GetMapping("/roleForm")
    public String mostrarFormulario(@RequestParam("role") Integer id, Model model) {
        Role role = null;
        role = roleService.obtenerRolePorId(id); // Método para obtener el rol por id
        model.addAttribute("role", role);
        return "roles/formularios_role";
    }

}
