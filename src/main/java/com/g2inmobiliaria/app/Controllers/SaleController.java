package com.g2inmobiliaria.app.Controllers;

import com.g2inmobiliaria.app.Entities.Agreement;
import com.g2inmobiliaria.app.Entities.PaymentMethod;
import com.g2inmobiliaria.app.Entities.Sale;
import com.g2inmobiliaria.app.Services.AgreementService;
import com.g2inmobiliaria.app.Services.PaymentMethodService;
import com.g2inmobiliaria.app.Services.SaleService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;
    @Autowired
    private PaymentMethodService paymentMethodService;
    @Autowired
    private AgreementService agreementService;

    private List<PaymentMethod> paymentMethods;

    @PostConstruct
    public void init() {
        paymentMethods = paymentMethodService.listarPaymentMethods();
    }

    @GetMapping("/listarVentas")
    public String listSales(Model model) {
        List<Sale> salesList = saleService.getSales(null, null, null, null, null, null);
        model.addAttribute("sales", salesList);
        return "ventas/ventas_admin";
    }

    @PostMapping("/registrarSale")
    public ResponseEntity<String> registerSale(@RequestBody Sale sale) {
        String response = saleService.createSale(sale);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/actualizarSale")
    public ResponseEntity<String> updateSale(@RequestBody Sale sale) {
        String response = saleService.updateSale(sale);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/eliminarSale")
    public ResponseEntity<String> logicalDeleteSale(@RequestParam("sale") int id) {
        String response = saleService.logicalDeleteSale(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/saleForm")
    public String showForm(@RequestParam("sale") Integer id, Model model) {
        Sale sale = null;
        if (id != null && id > 0) {
            List<Sale> sales = saleService.getSales(id, null, null, null, null, null);
            if (!sales.isEmpty()) {
                sale = sales.get(0);
            }
        }
        List<Agreement> agreements = agreementService.getAgreements(null, null, null, null, null);
        model.addAttribute("agreements", agreements);
        model.addAttribute("paymentMethods", paymentMethods);
        model.addAttribute("sale", sale);
        return "ventas/formularios_sale";
    }

    @GetMapping("/detalles")
    public String detalles(@RequestParam("agreement") Integer idAgreement, Model model) {
        Agreement agreement = (Agreement) agreementService.getAgreements(idAgreement, null, null, null, null).get(0);
        model.addAttribute("agreement", agreement);
        return "ventas/agreementDetails :: modalContent";
    }
}
