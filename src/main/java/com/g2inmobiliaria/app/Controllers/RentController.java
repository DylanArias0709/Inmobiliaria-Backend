package com.g2inmobiliaria.app.Controllers;

import com.g2inmobiliaria.app.Entities.Rent;
import com.g2inmobiliaria.app.Services.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rents")
public class RentController {

    @Autowired
    private RentService rentService;

    @GetMapping("/listarRentas")
    public String listRents(Model model) {
        List<Rent> rentList = rentService.getRents(null, null, null);
        model.addAttribute("rents", rentList);
        return "rentas/rentas_admin";
    }

    @PostMapping("/registrarRent")
    public ResponseEntity<?> registerRent(@RequestBody Rent rent) {
        return ResponseEntity.ok().body(rentService.createRent(rent));
    }

    @PostMapping("/actualizarRent")
    public ResponseEntity<?> updateRent(@RequestBody Rent rent) {
        return ResponseEntity.ok().body(rentService.updateRent(rent));
    }

    @DeleteMapping("/eliminarRent")
    public ResponseEntity<?> logicalDeleteRent(@RequestParam("rent") int id) {
        return ResponseEntity.ok().body(rentService.logicalDeleteRent(id));
    }

    @GetMapping("/rentForm")
    public String showForm(@RequestParam("rent") Integer id, Model model) {
        Rent rent = null;
        if (id != null && id > 0) {
            List<Rent> rents = rentService.getRents(id, null, null);
            if (!rents.isEmpty()) {
                rent = rents.getFirst();
            }
        }
        model.addAttribute("rent", rent);
        return "rentas/formularios_rent";
    }
}
