package com.g2inmobiliaria.app.Services;

import com.g2inmobiliaria.app.Entities.Province;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvincesService {
    @Autowired
    private EntityManager entityManager;

    // Método para listar Provinces
    public List<Province> listarProvinces() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spGetProvince", Province.class);
        return query.getResultList();
    }

    // Método para registrar una Province y obtener el ID de la province creada
    public String registrarProvince(Province province) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spCreateProvince");
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT); // Parámetro de salida para el ID de la province creada

        query.setParameter(1, province.getName());

        query.execute();

        // Obtener el valor del parámetro de salida
        int idProvinceCreada = (int) query.getOutputParameterValue(2);

        if(idProvinceCreada > 0){
            return "Province creada exitosamente";
        } else if (idProvinceCreada == -1) {
            return "El nombre de la province ya existe";
        } else{
            return "Hubo un error al registrar la province";
        }
    }

    public String actualizarProvince(Province province) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spUpdateProvince");
        query.registerStoredProcedureParameter("IdProvince", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Name", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("ErrorCode", Integer.class, ParameterMode.OUT);

        query.setParameter("IdProvince", province.getId()); // Assuming you have a getId() method in your Province entity
        query.setParameter("Name", province.getName());

        query.execute();

        int errorCode = (int) query.getOutputParameterValue("ErrorCode");
        System.out.println(errorCode);
        if (errorCode == 0){
            return "Province actualizada exitosamente";
        } else {
            return "Hubo un error actualizando la province";
        }
    }

    public String borradoLogicoProvince(int id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spLogicalDeleteProvince");
        query.registerStoredProcedureParameter("IdProvince", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("IdProvinceEliminada", Integer.class, ParameterMode.OUT);

        query.setParameter("IdProvince", id);

        query.execute();

        int idProvinceEliminada = (int) query.getOutputParameterValue("IdProvinceEliminada");
        System.out.println(idProvinceEliminada);
        if (idProvinceEliminada == id){
            return "{\"success\": true, \"message\": \"Province deshabilitada exitosamente.\"}";
        } else {
            return "{\"success\": false, \"message\": \"Hubo un error deshabilitando la province.\"}";
        }
    }
}
