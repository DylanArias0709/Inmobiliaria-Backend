package com.g2inmobiliaria.app.Services;

import com.g2inmobiliaria.app.Entities.RealStateAgent;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class RealStateAgentService {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public List<Object[]> getRealEstateAgents(Integer idAgent) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spGetRealStateAgent");

        query.registerStoredProcedureParameter("IdRealStateAgent", Integer.class, ParameterMode.IN);
        query.setParameter("IdRealStateAgent", idAgent);

        List<Object[]> results = query.getResultList();

        return results;
    }

    @Transactional
    public Map<String, Object> registerRealStateAgent(RealStateAgent agente, String telefono, String email) {
        // Lógica para registrar un nuevo agente
        // Llamar a los procedimientos almacenados necesarios para registrar el agente
        // Retornar un mapa con el resultado de la operación
        return Map.of("success", true, "message", "Agent registered successfully");
    }

    @Transactional
    public Map<String, Object> updateRealStateAgent(RealStateAgent agente, String telefono, String email) {
        // Lógica para actualizar un agente existente
        // Llamar a los procedimientos almacenados necesarios para actualizar el agente
        // Retornar un mapa con el resultado de la operación
        return Map.of("success", true, "message", "Agent updated successfully");
    }

    @Transactional
    public RealStateAgent getRealStateAgentById(Integer id) {
        // Lógica para obtener un agente por su ID
        // Implementar esta función para buscar y retornar el agente usando JPA o consulta directa
        return entityManager.find(RealStateAgent.class, id);
    }

    @Transactional
    public void logicalDeleteRealStateAgent(Integer realStateAgentId) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spLogicalDeleteRealStateAgent");
        query.registerStoredProcedureParameter("RealStateAgentId", Integer.class, ParameterMode.IN);
        query.setParameter("RealStateAgentId", realStateAgentId);

        query.execute();
    }
}
