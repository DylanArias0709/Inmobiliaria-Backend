package com.g2inmobiliaria.app.Controllers;

import com.g2inmobiliaria.app.Entities.District;
import com.g2inmobiliaria.app.Services.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/district")
public class DistrictController {

    @Autowired
    private DistrictService districtService;

    @GetMapping("/listarDistrict")
    public String listarDistritos(Model model) {
        try {
            List<District> listaDistritos = districtService.listarDistritos();
            model.addAttribute("distritos", listaDistritos);
        } catch (Exception e) {
            // Manejo de errores: puedes registrar el error o mostrar un mensaje genérico
            model.addAttribute("error", "Error al cargar la lista de distritos.");
            e.printStackTrace(); // Aquí puedes usar un logger adecuado
        }
        return "district/district_admin";
    }


    @PostMapping("/registrarDistrict")
    public ResponseEntity<String> registrarDistrito(@RequestBody District district) {
        String mensaje = districtService.registrarDistrito(district);
        return ResponseEntity.ok().body(mensaje);
    }

    @PutMapping("/actualizarDistrict")
    public ResponseEntity<String> actualizarDistrito(@RequestBody District district) {
        String mensaje = districtService.actualizarDistrito(district);
        return ResponseEntity.ok().body(mensaje);
    }

    @DeleteMapping("/eliminarDistrict")
    public ResponseEntity<String> borradoLogicoDistrito(@RequestParam("id") int id) {
        String mensaje = districtService.borradoLogicoDistrito(id);
        return ResponseEntity.ok().body(mensaje);
    }
}
