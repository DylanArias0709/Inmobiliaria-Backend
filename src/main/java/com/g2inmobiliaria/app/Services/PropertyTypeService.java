package com.g2inmobiliaria.app.Services;

import com.g2inmobiliaria.app.Entities.PropertyType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PropertyTypeService {

    @Autowired
    private EntityManager entityManager;

    public List<PropertyType> listPropertyTypes() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spGetPropertyType", PropertyType.class);
        return query.getResultList();
    }

    public String createPropertyType(PropertyType propertyType) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spCreatePropertyType");
        query.registerStoredProcedureParameter("PropertyTypeName", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("PropertyTypeDescription", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("NewPropertyTypeID", Integer.class, ParameterMode.OUT);

        query.setParameter("PropertyTypeName", propertyType.getPropertyTypeName());
        query.setParameter("PropertyTypeDescription", propertyType.getPropertyTypeDescription());

        query.execute();

        int newPropertyTypeID = (int) query.getOutputParameterValue("NewPropertyTypeID");

        if (newPropertyTypeID > 0) {
            return "{\"success\": true, \"message\": \"¡Tipo de propiedad creado exitosamente!\"}";
        } else if (newPropertyTypeID == -1) {
            return "{\"success\": false, \"message\": \"¡El nombre del tipo de propiedad ya existe!\"}";
        } else {
            return "{\"success\": false, \"message\": \"¡Hubo un error creando el tipo de propiedad!\"}";
        }
    }





    public String updatePropertyType(PropertyType propertyType) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spUpdatePropertyType");

        query.registerStoredProcedureParameter("IdPropertyType", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("NewNamePropertyType", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("NewNamePropertyTypeDescription", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Status", Short.class, ParameterMode.IN);

        query.setParameter("IdPropertyType", propertyType.getId());
        query.setParameter("NewNamePropertyType", propertyType.getPropertyTypeName());
        query.setParameter("NewNamePropertyTypeDescription", propertyType.getPropertyTypeDescription());
        query.setParameter("Status", propertyType.getStatus());

        query.execute();

        return "{\"success\": true, \"message\": \"¡Tipo de propiedad actualizado exitosamente!\"}";
    }







    public String logicalDeletePropertyType(int id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spLogicalDeletePropertyType");
        query.registerStoredProcedureParameter("IdPropertyType", Integer.class, ParameterMode.IN);

        query.setParameter("IdPropertyType", id);

        query.execute();

        // Asumir que si no hay excepción, la operación fue exitosa
        return "{\"success\": true, \"message\": \"Tipo de propiedad deshabilitada exitosamente.\"}";
    }


    public PropertyType getPropertyTypeById(int id){
        return entityManager.find(PropertyType.class, id);
    }


}