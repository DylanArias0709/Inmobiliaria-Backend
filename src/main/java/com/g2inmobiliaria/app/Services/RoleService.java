package com.g2inmobiliaria.app.Services;

import com.g2inmobiliaria.app.Entities.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private EntityManager entityManager;

    // Método para listar entidades
    public List<Role> listarRoles() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spGetRole");
        return query.getResultList();
    }


}
