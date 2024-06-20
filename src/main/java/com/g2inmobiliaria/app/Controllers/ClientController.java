package com.g2inmobiliaria.app.Controllers;

import com.g2inmobiliaria.app.Dto.ClientDTO;
import com.g2inmobiliaria.app.Entities.Client;
import com.g2inmobiliaria.app.Services.ClientService;
import com.g2inmobiliaria.app.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clientes")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/listarClientes")
    public List<Object[]> listarClientes(@RequestParam("idCliente") Integer idCliente) {
        if(idCliente == 0){
            idCliente = null;
        }
        return clientService.getClients(idCliente);
    }

    @PostMapping("/registrarCliente")
    public String registrarCliente(@RequestBody Map<String, Object> clienteData){
        Client cliente = new Client();
        cliente.getIdUser().getIdPerson().setName((String) clienteData.get("nombre"));
        cliente.getIdUser().getIdPerson().setFirstSurname((String) clienteData.get("primerApellido"));
        cliente.getIdUser().getIdPerson().setFirstSurname((String) clienteData.get("segundoApellido"));
        cliente.getIdUser().getIdPerson().setFirstSurname((String) clienteData.get("idCard"));
        String provincia = (String) clienteData.get("idProvince");
        cliente.getIdUser().getIdPerson().getIdDirection().getIdDistrict().getIdCanton().getIdProvince().setId(Integer.parseInt(provincia));
        String canton = (String) clienteData.get("idCanton");
        cliente.getIdUser().getIdPerson().getIdDirection().getIdDistrict().getIdCanton().setId(Integer.parseInt(canton));
        String distrito = (String) clienteData.get("idDistrict");
        cliente.getIdUser().getIdPerson().getIdDirection().getIdDistrict().setId(Integer.parseInt(distrito));
        cliente.getIdUser().getIdPerson().getIdDirection().setAditionalInformation((String) clienteData.get("aditionalInformation"));
        String email = (String) clienteData.get("correoElectronico");
        String telefono = (String) clienteData.get("numeroTelefono");
        cliente.getIdUser().setUserName((String) clienteData.get("userName"));
        cliente.getIdUser().setPassword((String) clienteData.get("password"));
        cliente.getIdUser().setPassword((String) clienteData.get("activationToken"));
        cliente.getIdUser().setPassword((String) clienteData.get("verificationToken"));
        cliente.getIdUser().setPassword((String) clienteData.get("budget"));
        cliente.getIdUser().getIdRole().setId(Integer.parseInt((String) clienteData.get("idRole")));

        return clientService.registerClient(cliente, telefono, email);
    }

    @PostMapping("/actualizarCliente")
    public String actualizarCliente(@RequestBody Map<String, Object> clienteData){
        Client cliente = new Client();
        int idCliente = Integer.parseInt(clienteData.get("idCliente").toString());
        cliente.setId(idCliente);
        cliente.getIdUser().getIdPerson().setName((String) clienteData.get("nombre"));
        cliente.getIdUser().getIdPerson().setFirstSurname((String) clienteData.get("primerApellido"));
        cliente.getIdUser().getIdPerson().setFirstSurname((String) clienteData.get("segundoApellido"));
        cliente.getIdUser().getIdPerson().setFirstSurname((String) clienteData.get("idCard"));
        String provincia = (String) clienteData.get("idProvince");
        cliente.getIdUser().getIdPerson().getIdDirection().getIdDistrict().getIdCanton().getIdProvince().setId(Integer.parseInt(provincia));
        String canton = (String) clienteData.get("idCanton");
        cliente.getIdUser().getIdPerson().getIdDirection().getIdDistrict().getIdCanton().setId(Integer.parseInt(canton));
        String distrito = (String) clienteData.get("idDistrict");
        cliente.getIdUser().getIdPerson().getIdDirection().getIdDistrict().setId(Integer.parseInt(distrito));
        cliente.getIdUser().getIdPerson().getIdDirection().setAditionalInformation((String) clienteData.get("aditionalInformation"));
        String email = (String) clienteData.get("correoElectronico");
        String telefono = (String) clienteData.get("numeroTelefono");
        cliente.getIdUser().setUserName((String) clienteData.get("userName"));
        cliente.getIdUser().setPassword((String) clienteData.get("password"));
        cliente.getIdUser().setPassword((String) clienteData.get("activationToken"));
        cliente.getIdUser().setPassword((String) clienteData.get("verificationToken"));
        cliente.getIdUser().setPassword((String) clienteData.get("budget"));
        cliente.getIdUser().getIdRole().setId(Integer.parseInt((String) clienteData.get("idRole")));

        return clientService.updateClient(cliente, telefono, email);
    }
}
