package com.g2inmobiliaria.app.Controllers;

import com.g2inmobiliaria.app.Entities.ComunicationType;
import com.g2inmobiliaria.app.Services.ComunicationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comunication-types")
public class ComunicationTypeController {

    @Autowired
    private ComunicationTypeService comunicationTypeService;

    @GetMapping("/list")
    public String listComunicationTypes(Model model) {
        List<ComunicationType> comunicationTypes = comunicationTypeService.listarComunicationTypes();
        model.addAttribute("comunicationTypes", comunicationTypes);
        return "comunication_types/comunication_types_admin";
    }

    @PostMapping("/create")
    public ResponseEntity<?> createComunicationType(@RequestBody ComunicationType comunicationType) {
        return ResponseEntity.ok().body(comunicationTypeService.registrarComunicationType(comunicationType));
    }


    @PostMapping("/update")
    public ResponseEntity<?> updateComunicationType(@RequestBody ComunicationType comunicationType) {
        return ResponseEntity.ok().body(comunicationTypeService.actualizarComunicationType(comunicationType));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteComunicationType(@RequestParam("comunicationType") int id){
        return ResponseEntity.ok().body(comunicationTypeService.borradoLogicoComunicationType(id));
    }

    @GetMapping("/form")
    public String showForm(@RequestParam("comunicationType") Integer id, Model model) {
        ComunicationType comunicationType = comunicationTypeService.obtenerComunicationTypePorId(id);
        model.addAttribute("comunicationType", comunicationType);
        return "comunication_types/comunication_type_form";
    }
}
