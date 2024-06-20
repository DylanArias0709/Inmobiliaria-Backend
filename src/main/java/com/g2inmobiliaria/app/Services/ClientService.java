package com.g2inmobiliaria.app.Services;

import com.g2inmobiliaria.app.Dto.ClientDTO;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class ClientService {
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public List<Object[]> getClients(Integer idClient) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spGetClient");

        // Configurar los parámetros del procedimiento almacenado
        query.registerStoredProcedureParameter("IdClient", Integer.class, ParameterMode.IN);
        query.setParameter("IdClient", idClient);

        // Ejecutar el procedimiento almacenado y obtener los resultados como un objeto List<Object[]>
        List<Object[]> results = query.getResultList();

        return results;
    }
}
