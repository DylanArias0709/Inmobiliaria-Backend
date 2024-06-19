package com.g2inmobiliaria.app.Services;

import com.g2inmobiliaria.app.Entities.District;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictService {
    @Autowired
    private EntityManager entityManager;

    // Método para listar Distritos
    public List<District> listarDistritos() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spGetDistrict", District.class);
        return query.getResultList();
    }

    // Método para registrar un Distrito y obtener el ID del distrito creado
    public String registrarDistrito(District district) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spCreateDistrict");
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.OUT); // Parámetro de salida para el ID del distrito creado

        query.setParameter(1, district.getName());
        query.setParameter(2, district.getIdCanton());

        query.execute();

        // Obtener el valor del parámetro de salida
        int idDistritoCreado = (int) query.getOutputParameterValue(3);

        if(idDistritoCreado > 0){
            return "Distrito creado exitosamente";
        } else if (idDistritoCreado == -1) {
            return "El nombre del distrito ya existe";
        } else {
            return "Hubo un error al registrar el distrito";
        }
    }

    public String actualizarDistrito(District district) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spUpdateDistrict");
        query.registerStoredProcedureParameter("IdDistrict", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Name", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Status", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("ErrorCode", Integer.class, ParameterMode.OUT);

        query.setParameter("IdDistrict", district.getId());
        query.setParameter("Name", district.getName());
        query.setParameter("Status", district.getStatus());

        query.execute();

        int errorCode = (int) query.getOutputParameterValue("ErrorCode");
        System.out.println(errorCode);
        if (errorCode == 0) {
            return "Distrito actualizado exitosamente";
        } else {
            return "Hubo un error actualizando el distrito";
        }
    }

    public String borradoLogicoDistrito(int id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spLogicalDeleteDistrict");
        query.registerStoredProcedureParameter("IdDistrict", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("IdDistrictEliminado", Integer.class, ParameterMode.OUT);

        query.setParameter("IdDistrict", id);

        query.execute();

        int idDistritoEliminado = (int) query.getOutputParameterValue("IdDistrictEliminado");
        System.out.println(idDistritoEliminado);
        if (idDistritoEliminado == id) {
            return "{\"success\": true, \"message\": \"Distrito deshabilitado exitosamente.\"}";
        } else {
            return "{\"success\": false, \"message\": \"Hubo un error deshabilitando el distrito.\"}";
        }
    }
}
