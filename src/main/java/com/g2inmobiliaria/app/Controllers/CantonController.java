package com.g2inmobiliaria.app.Controllers;


import com.g2inmobiliaria.app.Entities.Canton;
import com.g2inmobiliaria.app.Entities.Province;
import com.g2inmobiliaria.app.Entities.Role;
import com.g2inmobiliaria.app.Services.CantonService;
import com.g2inmobiliaria.app.Services.ProvincesService;
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

    @Autowired
    private ProvincesService provinceService;

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

    @GetMapping("/cantonForm")
    public String mostrarFormulario(@RequestParam("canton") Integer id, Model model) {
        Canton canton = null;
        if (id != 0) {
            canton = cantonService.obtenerRolePorId(id);
        }
        List<Province> listaProvince = provinceService.listarProvince();
        model.addAttribute("provinces", listaProvince);
        model.addAttribute("canton", canton);
        return "Canton/formularios_canton";
    }

    @PostMapping("/registrarCanton")
    public ResponseEntity<?> registrarCanton(@RequestParam("cantonName") String cantonName, @RequestParam("provinceId") int provinceId) {
        Canton canton = new Canton();
        // Configurar los atributos del objeto Canton según los parámetros recibidos
        canton.setName(cantonName);
        canton.getIdProvince().setId(provinceId);
        canton.setStatus((short) 1);

        // Llamar al servicio para registrar el cantón
        String response = cantonService.registrarCanton(canton);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/actualizarCanton")
    public ResponseEntity<?> actualizarCanton(
            @RequestParam("idCanton") int idCanton,
            @RequestParam("cantonName") String cantonName,
            @RequestParam("provinceId") int provinceId) {

        Canton canton = new Canton();
        // Configurar los atributos del objeto Canton según los parámetros recibidos
        canton.setId(idCanton);
        canton.setName(cantonName);
        canton.getIdProvince().setId(provinceId);
        canton.setStatus((short) 1); // Estatus quemado en 1

        // Llamar al servicio para actualizar el cantón
        String response = cantonService.actualizarCanton(canton);
        return ResponseEntity.ok().body(response);
    }


}
