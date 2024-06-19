package com.g2inmobiliaria.app.Controllers;


import com.g2inmobiliaria.app.Entities.Canton;
import com.g2inmobiliaria.app.Entities.Role;
import com.g2inmobiliaria.app.Services.CantonService;
import com.g2inmobiliaria.app.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/canton")
public class CantonController {

    @Autowired
    private CantonService cantonService;

    @GetMapping("/listarCantones")
    public String listarEntidades(Model model) {
        List<Canton> listaCantones = cantonService.listarCantones();
        model.addAttribute("cantones", listaCantones);
        return "Canton/Canton";
    }

    @DeleteMapping("/eliminarCanton")
    public ResponseEntity<?> borradoLogicoCanton(@RequestParam("canton") int id){
        return ResponseEntity.ok().body(cantonService.borradoLogicCanton(id));
    }
}
