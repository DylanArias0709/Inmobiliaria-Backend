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
        query.registerStoredProcedureParameter("NameServiceType", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("NewServiceTypeID", Integer.class, ParameterMode.OUT);

        query.setParameter("NameServiceType", serviceType.getNameServiceType());

        query.execute();

        int newServiceTypeID = (int) query.getOutputParameterValue("NewServiceTypeID");

        if (newServiceTypeID > 0) {
            return "{\"success\": true, \"message\": \"¡Tipo de servicio creado exitosamente!\"}";
        } else if (newServiceTypeID == -1) {
            return "{\"success\": false, \"message\": \"¡El nombre del tipo de servicio ya existe!\"}";
        } else {
            return "{\"success\": false, \"message\": \"¡Hubo un error creando el tipo de servicio!\"}";
        }
    }


    // Método para actualizar un tipo de servicio
    public String actualizarServiceType(ServiceType serviceType) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spUpdateServiceType");
        query.registerStoredProcedureParameter("IdServiceType", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Status", Short.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("NewNameServiceType", String.class, ParameterMode.IN);

        query.setParameter("IdServiceType", serviceType.getId());
        query.setParameter("Status", serviceType.getStatus());
        query.setParameter("NewNameServiceType", serviceType.getNameServiceType());

        query.execute();

        // No necesitamos obtener parámetros de salida explícitamente si el procedimiento no los devuelve

        return "{\"success\": true, \"message\": \"¡Tipo de servicio actualizado exitosamente!\"}";
    }



    // Método para eliminar lógicamente un tipo de servicio
    public String borradoLogicoServiceType(int id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spLogicalDeleteServiceType");
        query.registerStoredProcedureParameter("IdServiceType", Integer.class, ParameterMode.IN);
        query.setParameter("IdServiceType", id);

        query.execute();

        // No necesitas obtener el parámetro de salida si el procedimiento no devuelve uno
        // int idServiceTypeEliminado = (int) query.getOutputParameterValue("IdServiceTypeEliminado");

        // Suponiendo que el procedimiento solo cambia el estado y no devuelve ningún valor significativo
        return "{\"success\": true, \"message\": \"Tipo de servicio deshabilitado exitosamente.\"}";
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
