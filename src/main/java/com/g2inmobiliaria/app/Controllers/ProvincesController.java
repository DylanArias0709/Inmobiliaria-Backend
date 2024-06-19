package com.g2inmobiliaria.app.Controllers;

import com.g2inmobiliaria.app.Entities.Province;
import com.g2inmobiliaria.app.Entities.Role;
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
    public String listarProvincias(Model model) {
        List<Province> listaProvinces = provincesService.listarProvince();
        model.addAttribute("provinces", listaProvinces);
        return "provinces/provinces_admin";
    }

    @PostMapping("/registrarProvince")
    public ResponseEntity<?> registrarProvince(@RequestBody Province province) {
        return ResponseEntity.ok().body(provincesService.registrarProvince(province));
    }

    @PostMapping("/actualizarProvince")
    public ResponseEntity<?> actualizarProvince(@RequestBody Province province) {
        return ResponseEntity.ok().body(provincesService.actualizarProvince(province));
    }

    @DeleteMapping("/eliminarProvince")
    public ResponseEntity<?> borradoLogicoProvince(@RequestParam("province") int id){
        return ResponseEntity.ok().body(provincesService.borradoLogicoProvince(id));
    }

    @GetMapping("/provinceForm")
    public String mostrarFormulario(@RequestParam("province") Integer id, Model model) {
        Province province = provincesService.obtenerProvincePorId(id); // Método para obtener la provincia por id
        model.addAttribute("province", province);
        return "provinces/formularios_province";
    }
}
