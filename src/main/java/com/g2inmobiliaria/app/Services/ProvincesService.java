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

    // Método para listar Provincias
    public List<Province> listarProvince() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spGetProvince", Province.class);
        return query.getResultList();
    }

    // Método para registrar una Provincia y obtener el ID de la provincia creada
    public String registrarProvince(Province province) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spCreateProvince");
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, Short.class, ParameterMode.IN); // Parámetro de salida para el ID de la provincia creada
        query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.OUT); // Parámetro de salida para el ID de la provincia creada

        query.setParameter(1, province.getName());
        query.setParameter(2, province.getStatus());

        query.execute();

        // Obtener el valor del parámetro de salida
        int idProvinceCreada = (int) query.getOutputParameterValue(3);

        if(idProvinceCreada > 0){
            return "{\"success\": true, \"message\": \"¡Provincia creado exitosamente!\"}";
        } else if (idProvinceCreada == -1) {
            return "{\"success\": false, \"message\": \"¡El nombre de la provincia ya existe!\"}";
        } else{
            return "{\"success\": false, \"message\": \"¡Hubo un error creandola provincia!\"}";
        }
    }

    public String actualizarProvince(Province province) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spUpdateProvince");
        query.registerStoredProcedureParameter("IdProvince", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Name", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Status", Byte.class, ParameterMode.IN); // TINYINT corresponde a Byte en Java
        query.registerStoredProcedureParameter("ErrorCode", Integer.class, ParameterMode.OUT);

        query.setParameter("IdProvince", province.getId()); // Assuming you have a getId() method in your Province entity
        query.setParameter("Name", province.getName());
        query.setParameter("Status", province.getStatus()); // Asumiendo que getStatus() devuelve un Byte


        query.execute();

        int errorCode = (int) query.getOutputParameterValue("ErrorCode");
        if (errorCode == 0){
            return "{\"success\": true, \"message\": \"¡Provincia actualizada exitosamente!\"}";
        } else {
            return "{\"success\": false, \"message\": \"¡Hubo un error actualizando!\"}";
        }
    }

    public String borradoLogicoProvince(int id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spLogicalDeleteProvince");
        query.registerStoredProcedureParameter("IdProvince", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("IdProvinceEliminada", Integer.class, ParameterMode.OUT);

        query.setParameter("IdProvince", id);

        query.execute();

        int idProvinceEliminada = (int) query.getOutputParameterValue("IdProvinceEliminada");

        if (idProvinceEliminada == id){
            return "{\"success\": true, \"message\": \"Provincia deshabilitada exitosamente.\"}";
        } else {
            return "{\"success\": false, \"message\": \"Hubo un error deshabilitando la provincia.\"}";
        }
    }

    public Province obtenerProvincePorId(int id){
        for(Province province : listarProvince()){
            if (id == province.getId()){
                return province;
            }
        }
        return null;
    }
}
