package com.g2inmobiliaria.app.Services;

import com.g2inmobiliaria.app.Entities.ComunicationType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComunicationTypeService {

    @Autowired
    private EntityManager entityManager;

    // Método para listar tipos de comunicación
    public List<ComunicationType> listarComunicationTypes() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spGetComunicationType", ComunicationType.class);
        return query.getResultList();
    }

    // Método para registrar un tipo de comunicación y obtener el ID creado
    public String registrarComunicationType(ComunicationType comunicationType) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spCreateComunicationType");
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN); // Nombre del tipo de comunicación
        query.setParameter(1, comunicationType.getNameComunicationType());

        query.execute();

        // Verificar el resultado del procedimiento almacenado
        List<Object[]> results = query.getResultList();
        if (!results.isEmpty()) {
            Object[] result = results.get(0);
            int resultCode = (int) result[0];
            String message = (String) result[1];

            if (resultCode == 1) {
                return "{\"success\": true, \"message\": \"" + message + "\"}";
            } else {
                return "{\"success\": false, \"message\": \"" + message + "\"}";
            }
        }

        return "{\"success\": false, \"message\": \"¡Hubo un error creando el tipo de comunicación!\"}";
    }


    // Método para actualizar un tipo de comunicación
    public String actualizarComunicationType(ComunicationType comunicationType) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spUpdateComunicationType");

        // Registro de parámetros de entrada
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);   // @IdComunicationType
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);    // @NameComunicationType
        query.registerStoredProcedureParameter(3, Short.class, ParameterMode.IN);     // @Status

        // Asignación de valores a los parámetros de entrada
        query.setParameter(1, comunicationType.getId());
        query.setParameter(2, comunicationType.getNameComunicationType());
        query.setParameter(3, comunicationType.getStatus());

        // Ejecución del procedimiento almacenado
        query.execute();
        
        // Aquí puedes manejar cualquier lógica adicional según el resultado de la ejecución del procedimiento almacenado
        return "{\"success\": true, \"message\": \"¡Tipo de comunicación actualizado exitosamente!\"}";
    }








    // Método para deshabilitar lógicamente un tipo de comunicación
    public String borradoLogicoComunicationType(int id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spLogicalDeleteComunicationType");
        query.registerStoredProcedureParameter("IdComunicationType", Integer.class, ParameterMode.IN);

        query.setParameter("IdComunicationType", id);

        query.execute();

        return "{\"success\": true, \"message\": \"Tipo de comunicación deshabilitado exitosamente.\"}";
    }


    // Método para obtener un tipo de comunicación por su ID
    public ComunicationType obtenerComunicationTypePorId(int id) {
        for (ComunicationType comunicationType : listarComunicationTypes()) {
            if (id == comunicationType.getId()) {
                return comunicationType;
            }
        }
        return null;
    }
}
