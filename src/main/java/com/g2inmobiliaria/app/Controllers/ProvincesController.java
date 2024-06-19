package com.g2inmobiliaria.app.Controllers;

import com.g2inmobiliaria.app.Entities.Province;
import com.g2inmobiliaria.app.Services.ProvincesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/provinces")
public class ProvincesController {
    @Autowired
    private ProvincesService provincesService;

    @GetMapping("/listarProvinces")
    public String listarProvinces(Model model) {
        List<Province> listaProvinces = provincesService.listarProvinces();
        model.addAttribute("provinces", listaProvinces);
        return "provinces/provinces_admin";
    }

    @PostMapping("/registrarProvince")
    public String registrarProvince(@RequestBody Province province) {
        return provincesService.registrarProvince(province);
    }

    @PutMapping("/actualizarProvince")
    public String actualizarProvince(@RequestBody Province province) {
        return provincesService.actualizarProvince(province);
    }

    @DeleteMapping("/eliminarProvince")
    public ResponseEntity<?> borradoLogicoProvince(@RequestParam("id") int id){
        return ResponseEntity.ok().body(provincesService.borradoLogicoProvince(id));
    }
}
