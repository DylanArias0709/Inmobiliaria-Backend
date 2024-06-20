package com.g2inmobiliaria.app.Controllers;

import com.g2inmobiliaria.app.Entities.PaymentMethod;
import com.g2inmobiliaria.app.Services.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/payment-methods")
public class PaymentMethodController {

    @Autowired
    private PaymentMethodService paymentMethodService;

    @GetMapping("/list")
    public String listPaymentMethods(Model model) {
        List<PaymentMethod> paymentMethods = paymentMethodService.listarPaymentMethods();
        model.addAttribute("paymentMethods", paymentMethods);
        return "payment_methods/payment_methods_admin";
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPaymentMethod(@RequestBody PaymentMethod paymentMethod) {
        return ResponseEntity.ok().body(paymentMethodService.registrarPaymentMethod(paymentMethod));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updatePaymentMethod(@RequestBody PaymentMethod paymentMethod) {
        return ResponseEntity.ok().body(paymentMethodService.actualizarPaymentMethod(paymentMethod));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePaymentMethod(@RequestParam("PaymentMethod") int id) {
        return ResponseEntity.ok().body(paymentMethodService.borradoLogicoPaymentMethod(id));
    }

    @GetMapping("/form")
    public String showPaymentMethodForm(@RequestParam("PaymentMethod") Integer id, Model model) {
        PaymentMethod paymentMethod = paymentMethodService.obtenerPaymentMethodPorId(id);
        model.addAttribute("paymentMethod", paymentMethod);
        return "payment_methods/payment_method_form";
    }
}
