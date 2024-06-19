package com.g2inmobiliaria.app.Controllers;

import com.g2inmobiliaria.app.Entities.Rent;
import com.g2inmobiliaria.app.Services.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rents")
public class RentController {

    @Autowired
    private RentService rentService;

    @PostMapping("/add")
    public ResponseEntity<?> createRent(@RequestBody Rent rent) {
        try {
            rentService.createRent(rent.getIdAgreement().getId(), rent.getRentPrice(),
                    java.sql.Date.valueOf(rent.getStartDateRent()), java.sql.Date.valueOf(rent.getEndDateRent()),
                    rent.getMonthDuration(), rent.getInitialDeposit());
            return ResponseEntity.ok(rent);
        } catch (RuntimeException e) {
            // Retornar solo el mensaje de error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRent(@PathVariable Integer id) {
        try {
            rentService.logicalDeleteRent(id);
            return ResponseEntity.ok("Rent deleted successfully");
        } catch (RuntimeException e) {
            // Retornar solo el mensaje de error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Rent>> getRents(
            @RequestParam(required = false) Integer idRent,
            @RequestParam(required = false) Integer idAgreement,
            @RequestParam(required = false) Short status) {
        try {
            List<Rent> rents = rentService.getRents(idRent, idAgreement, status);
            return ResponseEntity.ok(rents);
        } catch (RuntimeException e) {
            // Retornar solo el mensaje de error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRent(@PathVariable Integer id, @RequestBody Rent rent) {
        try {
            rentService.updateRent(id, rent.getIdAgreement().getId(), rent.getRentPrice(),
                    java.sql.Date.valueOf(rent.getStartDateRent()), java.sql.Date.valueOf(rent.getEndDateRent()),
                    rent.getMonthDuration(), rent.getInitialDeposit(), rent.getStatus());
            return ResponseEntity.ok(rent);
        } catch (RuntimeException e) {
            // Retornar solo el mensaje de error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}