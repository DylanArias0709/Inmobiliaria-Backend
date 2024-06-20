package com.g2inmobiliaria.app.Controllers;

import com.g2inmobiliaria.app.Entities.Agreement;
import com.g2inmobiliaria.app.Services.AgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/agreements")
public class AgreementController {

    @Autowired
    private AgreementService agreementService;

    @GetMapping("/listarAcuerdos")
    public String listAgreements(Model model) {
        List<Agreement> agreementsList = agreementService.getAgreements(null, null, null, null, null);
        model.addAttribute("agreements", agreementsList);
        return "acuerdos/acuerdos_admin";
    }

    @PostMapping("/registrarAgreement")
    public ResponseEntity<String> registerAgreement(@RequestBody Agreement agreement) {
        String response = agreementService.createAgreement(agreement);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/actualizarAgreement")
    public ResponseEntity<String> updateAgreement(@RequestBody Agreement agreement) {
        String response = agreementService.updateAgreement(agreement);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/eliminarAgreement")
    public ResponseEntity<String> logicalDeleteAgreement(@RequestParam("agreement") int id) {
        String response = agreementService.logicalDeleteAgreement(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/agreementForm")
    public String showForm(@RequestParam("agreement") Integer id, Model model) {
        Agreement agreement = null;
        if (id != null && id > 0) {
            List<Agreement> agreements = agreementService.getAgreements(id, null, null, null, null);
            if (!agreements.isEmpty()) {
                agreement = agreements.getFirst();
            }
        }
        model.addAttribute("agreement", agreement);
        return "acuerdos/formularios_agreement";
    }
}
