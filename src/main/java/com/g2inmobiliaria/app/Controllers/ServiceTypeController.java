package com.g2inmobiliaria.app.Controllers;

import com.g2inmobiliaria.app.Entities.ServiceType;
import com.g2inmobiliaria.app.Services.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/serviceTypes")
public class ServiceTypeController {

    @Autowired
    private ServiceTypeService serviceTypeService;

    @GetMapping("/listarServiceTypes")
    public String listarServiceTypes(Model model) {
        List<ServiceType> listaServiceTypes = serviceTypeService.listarServiceTypes();
        model.addAttribute("serviceTypes", listaServiceTypes);
        return "serviceTypes/serviceTypes_admin";
    }

    @PostMapping("/registrarServiceType")
    public ResponseEntity<?> registrarServiceType(@RequestBody ServiceType serviceType) {
        return ResponseEntity.ok().body(serviceTypeService.registrarServiceType(serviceType));
    }

    @PostMapping("/actualizarServiceType")
    public ResponseEntity<?> actualizarServiceType(@RequestBody ServiceType serviceType) {
        return ResponseEntity.ok().body(serviceTypeService.actualizarServiceType(serviceType));
    }

    @DeleteMapping("/eliminarServiceType")
    public ResponseEntity<?> borradoLogicoServiceType(@RequestParam("serviceType") int id){
        return ResponseEntity.ok().body(serviceTypeService.borradoLogicoServiceType(id));
    }

    @GetMapping("/serviceTypeForm")
    public String mostrarFormulario(@RequestParam("serviceType") Integer id, Model model) {
        ServiceType serviceType = serviceTypeService.obtenerServiceTypePorId(id);
        model.addAttribute("serviceType", serviceType);
        return "serviceTypes/formularios_serviceType";
    }
}
