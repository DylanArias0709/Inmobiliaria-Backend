package com.g2inmobiliaria.app.Services;

import com.g2inmobiliaria.app.Entities.Canton;
import com.g2inmobiliaria.app.Entities.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CantonService {
    @Autowired
    private EntityManager entityManager;

    public List<Canton> listarCantones() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spGetCanton", Canton.class);

        List<Canton> cantones = query.getResultList();
        return cantones;
    }

    public String borradoLogicCanton(int id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spDeleteCanton");
        query.registerStoredProcedureParameter("IdCanton", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("IdCantonEliminado", Integer.class, ParameterMode.OUT);

        query.setParameter("IdCanton", id);

        query.execute();

        int IdCantonEliminado = (int) query.getOutputParameterValue("IdCantonEliminado");
        System.out.println(IdCantonEliminado);
        if (IdCantonEliminado == id){
            return "{\"success\": true, \"message\": \"Canton deshabilitado exitosamente.\"}";
        } else {
            return "{\"success\": false, \"message\": \"Hubo un error desahbilitando el Canton.\"}";
        }
    }

}
