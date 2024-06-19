package com.g2inmobiliaria.app.Controllers;

import com.g2inmobiliaria.app.Entities.PaymentMethod;
import com.g2inmobiliaria.app.Services.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paymentMethods")
public class PaymentMethodController {

    @Autowired
    private PaymentMethodService paymentMethodService;

    @PostMapping
    public ResponseEntity<?> createPaymentMethod(@RequestBody String typePaymentMethod) {
        try {
            paymentMethodService.createPaymentMethod(typePaymentMethod);
            return ResponseEntity.ok("Payment method created successfully");
        } catch (RuntimeException e) {
            // Retornar solo el mensaje de error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePaymentMethod(@PathVariable Integer id) {
        try {
            paymentMethodService.logicalDeletePaymentMethod(id);
            return ResponseEntity.ok("Payment method deleted successfully");
        } catch (RuntimeException e) {
            // Retornar solo el mensaje de error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePaymentMethod(@PathVariable Integer id, @RequestBody PaymentMethod paymentMethod) {
        try {
            paymentMethodService.updatePaymentMethod(id, paymentMethod.getTypePaymentMethod(), paymentMethod.getStatus());
            return ResponseEntity.ok("Payment method updated successfully");
        } catch (RuntimeException e) {
            // Retornar solo el mensaje de error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<PaymentMethod>> getPaymentMethods(@RequestParam(required = false) Integer idPaymentMethod) {
        try {
            List<PaymentMethod> paymentMethods = paymentMethodService.getPaymentMethods(idPaymentMethod);
            return ResponseEntity.ok(paymentMethods);
        } catch (RuntimeException e) {
            // Retornar solo el mensaje de error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
