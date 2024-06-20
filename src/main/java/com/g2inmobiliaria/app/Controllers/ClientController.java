package com.g2inmobiliaria.app.Controllers;

import com.g2inmobiliaria.app.Dto.ClientDTO;
import com.g2inmobiliaria.app.Services.ClientService;
import com.g2inmobiliaria.app.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
