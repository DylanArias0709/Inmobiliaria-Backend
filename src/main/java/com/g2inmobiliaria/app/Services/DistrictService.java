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

    // Método para listar distritos
    public List<District> listarDistritos() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spGetDistrict", District.class);
        return query.getResultList();
    }

    // Método para registrar un distrito y obtener el ID del distrito creado
    public String registrarDistrito(District district) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spCreateDistrict");
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.OUT); // Parámetro de salida para el ID del distrito creado

        query.setParameter(1, district.getIdCanton().getId()); // Ajustar para obtener el ID del cantón
        query.setParameter(2, district.getName());

        query.execute();

        // Obtener el valor del parámetro de salida
        int idDistritoCreado = (int) query.getOutputParameterValue(3);

        if (idDistritoCreado > 0) {
            return "Distrito creado exitosamente";
        } else if (idDistritoCreado == -1) {
            return "El nombre del distrito ya existe";
        } else {
            return "Hubo un error al registrar el distrito";
        }
    }

    // Método para actualizar un distrito
    public String actualizarDistrito(District district) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spUpdateDistrict");
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, Integer.class, ParameterMode.IN);

        query.setParameter(1, district.getId());
        query.setParameter(2, district.getIdCanton().getId());
        query.setParameter(3, district.getName());
        query.setParameter(4, district.getStatus());

        query.execute();

        return "Distrito actualizado exitosamente";
    }

    // Método para el borrado lógico de un distrito
    public String borradoLogicoDistrito(int id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spLogicalDeleteDistrict");
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);

        query.setParameter(1, id);

        query.execute();

        return "Distrito deshabilitado exitosamente";
    }
}
