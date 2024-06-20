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

    public Canton obtenerRolePorId(int id){
        for(Canton canton : listarCantones()){
            if (id == canton.getId()){
                return canton;
            }
        }
        return null;
    }

    // Método para registrar un Cantón y obtener el ID del cantón creado
    public String registrarCanton(Canton canton) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spCreateCanton");
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, Integer.class, ParameterMode.OUT); // Parámetro de salida para el ID del cantón creado

        query.setParameter(1, canton.getIdProvince().getId());
        query.setParameter(2, canton.getName());
        query.setParameter(3, canton.getStatus());

        query.execute();

        // Obtener el valor del parámetro de salida
        int idCantonCreado = (int) query.getOutputParameterValue(4);

        if (idCantonCreado > 0) {
            return "{\"success\": true, \"message\": \"¡Cantón creado exitosamente!\"}";
        } else {
            return "{\"success\": false, \"message\": \"¡Hubo un error creando el cantón!\"}";
        }
    }


    public String actualizarCanton(Canton canton) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spUpdateCanton");
        query.registerStoredProcedureParameter("IdCanton", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Name", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Status", Byte.class, ParameterMode.IN); // TINYINT corresponde a Byte en Java
        query.registerStoredProcedureParameter("ErrorCode", Integer.class, ParameterMode.OUT);

        query.setParameter("IdCanton", canton.getId()); // Asumiendo que tienes un método getId() en tu entidad Canton
        query.setParameter("Name", canton.getName());
        query.setParameter("Status", canton.getStatus()); // Asumiendo que getStatus() devuelve un Byte

        query.execute();

        int errorCode = (int) query.getOutputParameterValue("ErrorCode");
        if (errorCode == 0){
            return "{\"success\": true, \"message\": \"¡Cantón actualizado exitosamente!\"}";
        } else {
            return "{\"success\": false, \"message\": \"¡Hubo un error actualizando!\"}";
        }
    }
}
