package com.g2inmobiliaria.app.Controllers;

import com.g2inmobiliaria.app.Entities.Client;
import com.g2inmobiliaria.app.Entities.ClientPreference;
import com.g2inmobiliaria.app.Services.ClientPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clientPreferences")
public class ClientPreferencesController {

    @Autowired
    private ClientPreferencesService clientPreferencesService;

    @GetMapping("/listarClientPreferences")
    public String listarClientPreferences(Model model) {
        List<ClientPreference> listaClientPreferences = clientPreferencesService.listarClientPreferences();
        model.addAttribute("clientPreferences", listaClientPreferences);
        return "clientPreferences/client_preferences_admin";
    }

    @PostMapping("/registrarClientPreference")
    public ResponseEntity<?> registrarClientPreference(@RequestBody ClientPreference clientPreference) {
        return ResponseEntity.ok().body(clientPreferencesService.registrarClientPreference(clientPreference));
    }

    @PostMapping("/actualizarClientPreference")
    public ResponseEntity<?> actualizarClientPreference(@RequestBody ClientPreference clientPreference) {
        return ResponseEntity.ok().body(clientPreferencesService.actualizarClientPreference(clientPreference));
    }

    @DeleteMapping("/eliminarClientPreference")
    public ResponseEntity<?> eliminarClientPreference(@RequestParam("clientPreference") int id) {
        return ResponseEntity.ok().body(clientPreferencesService.eliminarClientPreference(id));
    }

    @GetMapping("/clientPreferenceForm")
    public String mostrarFormulario(@RequestParam("clientPreference") Integer id, Model model) {
        ClientPreference clientPreference = clientPreferencesService.obtenerClientPreferencePorId(id);
        model.addAttribute("clientPreference", clientPreference);
        return "clientPreferences/formularios_client_preference";
    }

    /*
    @GetMapping("/nombreCliente")
    @ResponseBody
    public String obtenerNombreCliente(@RequestParam("id") Integer id) {
        return clientPreferencesService.obtenerNombreCliente(id);
    }
    */

}
