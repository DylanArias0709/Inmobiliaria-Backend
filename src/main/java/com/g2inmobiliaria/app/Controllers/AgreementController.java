package com.g2inmobiliaria.app.Controllers;

import com.g2inmobiliaria.app.Entities.Agreement;
import com.g2inmobiliaria.app.Services.AgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/agreements")
public class AgreementController {

    @Autowired
    private AgreementService agreementService;

    @PostMapping
    public ResponseEntity<?> createAgreement(@RequestBody Agreement agreement) {
        try {
            agreementService.createAgreement(agreement.getIdProperty().getId(), agreement.getIdClient().getId(),
                    agreement.getIdRealStateAgent().getId(), Date.valueOf(agreement.getAgreementDate()), agreement.getAditionalInformation());
            return ResponseEntity.ok(agreement);
        } catch (RuntimeException e) {
            // Retornar solo el mensaje de error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAgreement(@PathVariable Integer id) {
        try {
            agreementService.logicalDeleteAgreement(id);
            return ResponseEntity.ok("Agreement deleted successfully");
        } catch (RuntimeException e) {
            // Retornar solo el mensaje de error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/listAgreements")
    public ResponseEntity<List<Agreement>> getAgreements(
            @RequestParam(required = false) Integer idAgreement,
            @RequestParam(required = false) Integer idProperty,
            @RequestParam(required = false) Integer idClient,
            @RequestParam(required = false) Integer idRealStateAgent,
            @RequestParam(required = false) Short status) {
        try {
            List<Agreement> agreements = agreementService.getAgreements(idAgreement, idProperty, idClient, idRealStateAgent, status);
            return ResponseEntity.ok(agreements);
        } catch (RuntimeException e) {
            // Retornar solo el mensaje de error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agreement> updateAgreement(@PathVariable Integer id, @RequestBody Agreement agreement) {
        try {
            agreementService.updateAgreement(id, agreement.getIdProperty().getId(), agreement.getIdClient().getId(),
                    agreement.getIdRealStateAgent().getId(), Date.valueOf(agreement.getAgreementDate()), agreement.getAditionalInformation(), agreement.getStatus());
            return ResponseEntity.ok(agreement);
        } catch (RuntimeException e) {
            // Retornar solo el mensaje de error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
