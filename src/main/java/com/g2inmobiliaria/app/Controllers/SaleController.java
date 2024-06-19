package com.g2inmobiliaria.app.Controllers;

import com.g2inmobiliaria.app.Entities.Sale;
import com.g2inmobiliaria.app.Services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping
    public ResponseEntity<?> createSale(@RequestBody Sale sale) {
        try {
            saleService.createSale(sale.getIdAgreement().getId(), sale.getIdClient().getId(),
                    sale.getIdRealStateAgent().getId(), java.sql.Date.valueOf(sale.getSaleDate()), sale.getAditionalInformation(),
                    sale.getIdPaymentMethod().getId());
            return ResponseEntity.ok(sale);
        } catch (RuntimeException e) {
            // Retornar solo el mensaje de error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSale(@PathVariable Integer id) {
        try {
            saleService.logicalDeleteSale(id);
            return ResponseEntity.ok("Sale deleted successfully");
        } catch (RuntimeException e) {
            // Retornar solo el mensaje de error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<List<Sale>> getSales(
            @RequestParam(required = false) Integer idSale,
            @RequestParam(required = false) Integer idAgreement,
            @RequestParam(required = false) Integer idClient,
            @RequestParam(required = false) Integer idRealStateAgent,
            @RequestParam(required = false) Integer idPaymentMethod,
            @RequestParam(required = false) Short status) {
        try {
            List<Sale> sales = saleService.getSales(idSale, idAgreement, idClient, idRealStateAgent, idPaymentMethod, status);
            return ResponseEntity.ok(sales);
        } catch (RuntimeException e) {
            // Retornar solo el mensaje de error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSale(@PathVariable Integer id, @RequestBody Sale sale) {
        try {
            saleService.updateSale(id, sale.getIdAgreement().getId(), sale.getIdClient().getId(),
                    sale.getIdRealStateAgent().getId(), Date.valueOf(sale.getSaleDate()), sale.getAditionalInformation(),
                    sale.getIdPaymentMethod().getId(), sale.getStatus());
            return ResponseEntity.ok(sale);
        } catch (RuntimeException e) {
            // Retornar solo el mensaje de error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}