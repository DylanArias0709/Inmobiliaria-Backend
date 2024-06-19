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
        List<District> listaDistritos = districtService.listarDistritos();
        model.addAttribute("district", listaDistritos);
        return "district/district_admin";
    }

    @PostMapping("/registrarDistrict")
    public String registrarDistrito(@RequestBody District district) {
        return districtService.registrarDistrito(district);
    }

    @PutMapping("/actualizarDistrict")
    public String actualizarDistrito(@RequestBody District district) {
        return districtService.actualizarDistrito(district);
    }

    @DeleteMapping("/eliminarDistrict")
    public ResponseEntity<?> borradoLogicoDistrito(@RequestParam("id") int id){
        return ResponseEntity.ok().body(districtService.borradoLogicoDistrito(id));
    }
}
