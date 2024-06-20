package com.g2inmobiliaria.app.Services;

import com.g2inmobiliaria.app.Entities.ServiceType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceTypeService {

    @Autowired
    private EntityManager entityManager;

    // Método para listar tipos de servicio
    public List<ServiceType> listarServiceTypes() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spGetServiceType", ServiceType.class);
        return query.getResultList();
    }

    // Método para registrar un tipo de servicio y obtener el ID creado
    public String registrarServiceType(ServiceType serviceType) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spCreateServiceType");
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, Short.class, ParameterMode.IN); // Parámetro de salida para el ID creado
        query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.OUT); // Parámetro de salida para el ID creado

        query.setParameter(1, serviceType.getNameServiceType());
        query.setParameter(2, serviceType.getStatus());

        query.execute();

        int idServiceTypeCreado = (int) query.getOutputParameterValue(3);

        if(idServiceTypeCreado > 0){
            return "{\"success\": true, \"message\": \"¡Tipo de servicio creado exitosamente!\"}";
        } else if (idServiceTypeCreado == -1) {
            return "{\"success\": false, \"message\": \"¡El nombre del tipo de servicio ya existe!\"}";
        } else{
            return "{\"success\": false, \"message\": \"¡Hubo un error creando el tipo de servicio!\"}";
        }
    }

    // Método para actualizar un tipo de servicio
    public String actualizarServiceType(ServiceType serviceType) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spUpdateServiceType");
        query.registerStoredProcedureParameter("IdServiceType", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("NameServiceType", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Status", Short.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("ErrorCode", Integer.class, ParameterMode.OUT);

        query.setParameter("IdServiceType", serviceType.getId());
        query.setParameter("NameServiceType", serviceType.getNameServiceType());
        query.setParameter("Status", serviceType.getStatus());

        query.execute();

        int errorCode = (int) query.getOutputParameterValue("ErrorCode");
        if (errorCode == 0){
            return "{\"success\": true, \"message\": \"¡Tipo de servicio actualizado exitosamente!\"}";
        } else {
            return "{\"success\": false, \"message\": \"¡Hubo un error actualizando el tipo de servicio!\"}";
        }
    }

    // Método para eliminar lógicamente un tipo de servicio
    public String borradoLogicoServiceType(int id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spLogicalDeleteServiceType");
        query.registerStoredProcedureParameter("IdServiceType", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("IdServiceTypeEliminado", Integer.class, ParameterMode.OUT);

        query.setParameter("IdServiceType", id);

        query.execute();

        int idServiceTypeEliminado = (int) query.getOutputParameterValue("IdServiceTypeEliminado");

        if (idServiceTypeEliminado == id){
            return "{\"success\": true, \"message\": \"Tipo de servicio deshabilitado exitosamente.\"}";
        } else {
            return "{\"success\": false, \"message\": \"Hubo un error deshabilitando el tipo de servicio.\"}";
        }
    }

    // Método para obtener un tipo de servicio por su ID
    public ServiceType obtenerServiceTypePorId(int id){
        for(ServiceType serviceType : listarServiceTypes()){
            if (id == serviceType.getId()){
                return serviceType;
            }
        }
        return null;
    }
}
