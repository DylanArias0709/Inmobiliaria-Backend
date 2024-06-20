package com.g2inmobiliaria.app.Controllers;

import com.g2inmobiliaria.app.Dto.ClientDTO;
import com.g2inmobiliaria.app.Entities.Client;
import com.g2inmobiliaria.app.Services.ClientService;
import com.g2inmobiliaria.app.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/clientes")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/listarClientes")
    public String listarClientes(@RequestParam("idCliente") Integer idCliente, Model model) {
        if(idCliente == 0){
            idCliente = null;
        }
        List<Object[]> listaClientes = clientService.getClients(idCliente);
        model.addAttribute("clientes", listaClientes);
        return "Clientes/client_admin";
    }

    @PostMapping("/registrarCliente")
    public String registrarCliente(@RequestBody Map<String, Object> clienteData){
        Client cliente = new Client();
        cliente.getIdUser().getIdPerson().setName((String) clienteData.get("Name"));
        cliente.getIdUser().getIdPerson().setFirstSurname((String) clienteData.get("FirstSurname"));
        cliente.getIdUser().getIdPerson().setFirstSurname((String) clienteData.get("seconSurname"));
        cliente.getIdUser().getIdPerson().setIdCard((String) clienteData.get("IdCard"));
        String provincia = (String) clienteData.get("IdProvince");
        cliente.getIdUser().getIdPerson().getIdDirection().getIdDistrict().getIdCanton().getIdProvince().setId(Integer.parseInt(String.valueOf(provincia)));
        String canton = (String) clienteData.get("IdCanton");
        cliente.getIdUser().getIdPerson().getIdDirection().getIdDistrict().getIdCanton().setId(Integer.parseInt(String.valueOf(canton)));
        String distrito = (String) clienteData.get("IdDistrict");
        cliente.getIdUser().getIdPerson().getIdDirection().getIdDistrict().setId(Integer.parseInt(String.valueOf(distrito)));
        cliente.getIdUser().getIdPerson().getIdDirection().setAditionalInformation((String) clienteData.get("AditionalInformation"));
        String email = (String) clienteData.get("Email");
        String telefono = (String) clienteData.get("PhoneNumber");
        cliente.getIdUser().setUserName((String) clienteData.get("UserName"));
        cliente.getIdUser().setPassword((String) clienteData.get("Password"));
        cliente.getIdUser().setActivationToken((String) clienteData.get("ActivationToken"));
        cliente.getIdUser().setVerificationToken((String) clienteData.get("VerificationToken"));
        cliente.setBudget(new BigDecimal((String) clienteData.get("Budget")));
        cliente.getIdUser().getIdRole().setId(Integer.parseInt((String) clienteData.get("IdRole")));

        return clientService.registrarClient(cliente, telefono, email);
    }

    @PostMapping("/actualizarCliente")
    public String actualizarCliente(@RequestBody Map<String, Object> clienteData){
        Client cliente = new Client();
        String idCliente = (String) clienteData.get("IdCliente");
        cliente.setId(Integer.parseInt(String.valueOf(idCliente)));
        cliente.getIdUser().getIdPerson().setName((String) clienteData.get("Name"));
        cliente.getIdUser().getIdPerson().setFirstSurname((String) clienteData.get("FirstSurname"));
        cliente.getIdUser().getIdPerson().setFirstSurname((String) clienteData.get("seconSurname"));
        cliente.getIdUser().getIdPerson().setFirstSurname((String) clienteData.get("IdCard"));
        String provincia = (String) clienteData.get("IdProvince");
        cliente.getIdUser().getIdPerson().getIdDirection().getIdDistrict().getIdCanton().getIdProvince().setId(Integer.parseInt(String.valueOf(provincia)));
        String canton = (String) clienteData.get("IdCanton");
        cliente.getIdUser().getIdPerson().getIdDirection().getIdDistrict().getIdCanton().setId(Integer.parseInt(String.valueOf(canton)));
        String distrito = (String) clienteData.get("IdDistrict");
        cliente.getIdUser().getIdPerson().getIdDirection().getIdDistrict().setId(Integer.parseInt(String.valueOf(distrito)));
        cliente.getIdUser().getIdPerson().getIdDirection().setAditionalInformation((String) clienteData.get("AditionalInformation"));
        String email = (String) clienteData.get("Email");
        String telefono = (String) clienteData.get("PhoneNumber");
        cliente.getIdUser().setUserName((String) clienteData.get("UserName"));
        cliente.getIdUser().setPassword((String) clienteData.get("Password"));
        cliente.getIdUser().setActivationToken((String) clienteData.get("ActivationToken"));
        cliente.getIdUser().setVerificationToken((String) clienteData.get("VerificationToken"));
        cliente.setBudget(new BigDecimal((String) clienteData.get("Budget")));
        cliente.getIdUser().getIdRole().setId(Integer.parseInt((String) clienteData.get("IdRole")));

        return clientService.updateClient(cliente, telefono, email);
    }
}
