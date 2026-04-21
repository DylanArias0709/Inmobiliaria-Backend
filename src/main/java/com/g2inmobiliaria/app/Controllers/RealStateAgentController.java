package com.g2inmobiliaria.app.Controllers;

import com.g2inmobiliaria.app.Entities.RealStateAgent;
import com.g2inmobiliaria.app.Entities.User;
import com.g2inmobiliaria.app.Services.RealStateAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/agents")
public class RealStateAgentController {

    @Autowired
    private RealStateAgentService realStateAgentService;

    @GetMapping("/listarAgentes")
    public String listarAgentes(Model model) {
        List<Object[]> agents = realStateAgentService.getRealEstateAgents(null);
        model.addAttribute("agents", agents);
        return "agentes/agentes_admin";
    }

    @GetMapping("/agentForm")
    public String showForm(@RequestParam(value = "agent", required = false) Integer id, Model model) {
        RealStateAgent agent = null;
        if (id != null && id > 0) {
            List<Object[]> agents = realStateAgentService.getRealEstateAgents(id);
            if (!agents.isEmpty()) {
                Object[] agentData = agents.get(0);
                agent = new RealStateAgent();
                User user = new User();
                agent.setId((Integer) agentData[0]);
                user.setUserName((String) agentData[3]);
                agent.setIdUser(user);
            }
        }
        model.addAttribute("agent", agent);
        return "agentes/formulario_agente";
    }

    @PostMapping("/registrarAgente")
    @ResponseBody
    public Map<String, Object> registrarAgente(@RequestBody Map<String, Object> agenteData) {
        RealStateAgent agente = new RealStateAgent();
        agente.getIdUser().getIdPerson().setName((String) agenteData.get("nombre"));
        agente.getIdUser().getIdPerson().setFirstSurname((String) agenteData.get("primerApellido"));
        agente.getIdUser().getIdPerson().setSecondSurname((String) agenteData.get("segundoApellido"));
        agente.getIdUser().getIdPerson().setIdCard((String) agenteData.get("idCard"));
        agente.getIdUser().getIdPerson().getIdDirection().setAditionalInformation((String) agenteData.get("aditionalInformation"));
        String email = (String) agenteData.get("correoElectronico");
        String telefono = (String) agenteData.get("numeroTelefono");
        agente.getIdUser().setUserName((String) agenteData.get("userName"));

        return realStateAgentService.registerRealStateAgent(agente, telefono, email);
    }

    @PostMapping("/actualizarAgente")
    @ResponseBody
    public Map<String, Object> actualizarAgente(@RequestBody Map<String, Object> agenteData) {
        Integer id = (Integer) agenteData.get("id");
        RealStateAgent agente = realStateAgentService.getRealStateAgentById(id);
        if (agente != null) {
            agente.getIdUser().getIdPerson().setName((String) agenteData.get("nombre"));
            agente.getIdUser().getIdPerson().setFirstSurname((String) agenteData.get("primerApellido"));
            agente.getIdUser().getIdPerson().setSecondSurname((String) agenteData.get("segundoApellido"));
            agente.getIdUser().getIdPerson().setIdCard((String) agenteData.get("idCard"));
            agente.getIdUser().getIdPerson().getIdDirection().setAditionalInformation((String) agenteData.get("aditionalInformation"));
            String email = (String) agenteData.get("correoElectronico");
            String telefono = (String) agenteData.get("numeroTelefono");
            agente.getIdUser().setUserName((String) agenteData.get("userName"));

            return realStateAgentService.updateRealStateAgent(agente, telefono, email);
        } else {
            // Handle agent not found case
            return Map.of("success", false, "message", "Agent not found");
        }
    }

    @DeleteMapping("/eliminarAgente")
    public String eliminarAgente(@RequestParam("agent") Integer id, Model model) {
        realStateAgentService.logicalDeleteRealStateAgent(id);
        return "redirect:/agents/listarAgentes";
    }
}
