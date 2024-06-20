package com.g2inmobiliaria.app.Services;


import com.g2inmobiliaria.app.Entities.Client;
import com.g2inmobiliaria.app.Entities.ClientPreference;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ClientPreferencesService {

    @Autowired
    private EntityManager entityManager;

    // Método para listar Preferencias de Clientes
    public List<ClientPreference> listarClientPreferences() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spGetClientPreference", ClientPreference.class);
        return query.getResultList();
    }

    // Método para registrar Preferencias de Cliente y obtener el ID creado
    public String registrarClientPreference(ClientPreference clientPreference) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spCreateClientPreference");
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.INOUT); // ID de Preferencia de Cliente (entrada y salida)
        query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);    // ID de Cliente
        query.registerStoredProcedureParameter(3, BigDecimal.class, ParameterMode.IN); // Precio mínimo
        query.registerStoredProcedureParameter(4, BigDecimal.class, ParameterMode.IN); // Precio máximo
        query.registerStoredProcedureParameter(5, Integer.class, ParameterMode.IN);    // Mínimo de habitaciones
        query.registerStoredProcedureParameter(6, Integer.class, ParameterMode.IN);    // Máximo de habitaciones
        query.registerStoredProcedureParameter(7, Integer.class, ParameterMode.IN);    // Mínimo de baños
        query.registerStoredProcedureParameter(8, Integer.class, ParameterMode.IN);    // Máximo de baños
        query.registerStoredProcedureParameter(9, Integer.class, ParameterMode.IN);    // Área mínima
        query.registerStoredProcedureParameter(10, Integer.class, ParameterMode.IN);   // Área máxima
        query.registerStoredProcedureParameter(11, Integer.class, ParameterMode.IN);   // Año de construcción mínimo
        query.registerStoredProcedureParameter(12, Integer.class, ParameterMode.IN);   // Año de construcción máximo
        query.registerStoredProcedureParameter(13, String.class, ParameterMode.IN);    // Características adicionales
        query.registerStoredProcedureParameter(14, Short.class, ParameterMode.IN);     // Estado

        query.setParameter(2, clientPreference.getIdClient().getId()); // ID del Cliente
        query.setParameter(3, clientPreference.getMinimumPrice());     // Precio mínimo
        query.setParameter(4, clientPreference.getMaximumPrice());     // Precio máximo
        query.setParameter(5, clientPreference.getAmountMinimunRooms());// Mínimo de habitaciones
        query.setParameter(6, clientPreference.getAmountMaximumRooms());// Máximo de habitaciones
        query.setParameter(7, clientPreference.getAmountMinimumBathrooms()); // Mínimo de baños
        query.setParameter(8, clientPreference.getAmountMaximumBathrooms()); // Máximo de baños
        query.setParameter(9, clientPreference.getMinimumArea());      // Área mínima
        query.setParameter(10, clientPreference.getMaximumArea());     // Área máxima
        query.setParameter(11, clientPreference.getMinimumYearConstruction()); // Año de construcción mínimo
        query.setParameter(12, clientPreference.getMaximumYearConstruction()); // Año de construcción máximo
        query.setParameter(13, clientPreference.getAdicionalCharacteristics()); // Características adicionales
        query.setParameter(14, clientPreference.getStatus());           // Estado

        query.execute();

        // Obtener el valor del parámetro de salida (ID de la preferencia de cliente creada)
        Integer idClientPreferenceCreada = (Integer) query.getOutputParameterValue(1);

        if (idClientPreferenceCreada != null && idClientPreferenceCreada > 0) {
            return "{\"success\": true, \"message\": \"¡Preferencia de cliente creada exitosamente!\"}";
        } else if (idClientPreferenceCreada != null && idClientPreferenceCreada == -1) {
            return "{\"success\": false, \"message\": \"¡El cliente no existe!\"}";
        } else {
            return "{\"success\": false, \"message\": \"¡Hubo un error creando la preferencia de cliente!\"}";
        }
    }

    // Método para actualizar Preferencias de Cliente
    public String actualizarClientPreference(ClientPreference clientPreference) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spUpdateClientPreference");
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);    // ID de Preferencia de Cliente
        query.registerStoredProcedureParameter(2, BigDecimal.class, ParameterMode.IN); // Precio mínimo
        query.registerStoredProcedureParameter(3, BigDecimal.class, ParameterMode.IN); // Precio máximo
        query.registerStoredProcedureParameter(4, Integer.class, ParameterMode.IN);    // Mínimo de habitaciones
        query.registerStoredProcedureParameter(5, Integer.class, ParameterMode.IN);    // Máximo de habitaciones
        query.registerStoredProcedureParameter(6, Integer.class, ParameterMode.IN);    // Mínimo de baños
        query.registerStoredProcedureParameter(7, Integer.class, ParameterMode.IN);    // Máximo de baños
        query.registerStoredProcedureParameter(8, Integer.class, ParameterMode.IN);    // Área mínima
        query.registerStoredProcedureParameter(9, Integer.class, ParameterMode.IN);    // Área máxima
        query.registerStoredProcedureParameter(10, Integer.class, ParameterMode.IN);   // Año de construcción mínimo
        query.registerStoredProcedureParameter(11, Integer.class, ParameterMode.IN);   // Año de construcción máximo
        query.registerStoredProcedureParameter(12, String.class, ParameterMode.IN);    // Características adicionales
        query.registerStoredProcedureParameter(13, Short.class, ParameterMode.IN);     // Estado
        query.registerStoredProcedureParameter(14, Integer.class, ParameterMode.OUT);  // Código de error

        query.setParameter(1, clientPreference.getId()); // ID de la Preferencia de Cliente
        query.setParameter(2, clientPreference.getMinimumPrice());     // Precio mínimo
        query.setParameter(3, clientPreference.getMaximumPrice());     // Precio máximo
        query.setParameter(4, clientPreference.getAmountMinimunRooms());// Mínimo de habitaciones
        query.setParameter(5, clientPreference.getAmountMaximumRooms());// Máximo de habitaciones
        query.setParameter(6, clientPreference.getAmountMinimumBathrooms()); // Mínimo de baños
        query.setParameter(7, clientPreference.getAmountMaximumBathrooms()); // Máximo de baños
        query.setParameter(8, clientPreference.getMinimumArea());      // Área mínima
        query.setParameter(9, clientPreference.getMaximumArea());     // Área máxima
        query.setParameter(10, clientPreference.getMinimumYearConstruction()); // Año de construcción mínimo
        query.setParameter(11, clientPreference.getMaximumYearConstruction()); // Año de construcción máximo
        query.setParameter(12, clientPreference.getAdicionalCharacteristics()); // Características adicionales
        query.setParameter(13, clientPreference.getStatus());           // Estado

        query.execute();

        // Obtener el código de error
        Integer errorCode = (Integer) query.getOutputParameterValue(14);

        if (errorCode == 0) {
            return "{\"success\": true, \"message\": \"¡Preferencia de cliente actualizada exitosamente!\"}";
        } else {
            return "{\"success\": false, \"message\": \"¡Hubo un error actualizando la preferencia de cliente!\"}";
        }
    }

    // Método para eliminar lógicamente Preferencias de Cliente por ID
    public String eliminarClientPreference(int id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spLogicalDeleteClientPreference");
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);    // ID de Preferencia de Cliente
        query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT);   // ID de Preferencia de Cliente eliminada

        query.setParameter(1, id);

        query.execute();

        // Obtener el ID de la Preferencia de Cliente eliminada
        Integer idClientPreferenceEliminada = (Integer) query.getOutputParameterValue(2);

        if (idClientPreferenceEliminada != null && idClientPreferenceEliminada == id) {
            return "{\"success\": true, \"message\": \"¡Preferencia de cliente eliminada exitosamente!\"}";
        } else {
            return "{\"success\": false, \"message\": \"¡Hubo un error eliminando la preferencia de cliente!\"}";
        }
    }

    // Método para obtener Preferencia de Cliente por ID
    public ClientPreference obtenerClientPreferencePorId(int id) {
        return entityManager.find(ClientPreference.class, id);
    }


    /*
    // Método para obtener el nombre completo del cliente asociado a unas Preferencias de Cliente
    public String obtenerNombreCliente(Integer id) {
        ClientPreference clientPreference = entityManager.find(ClientPreference.class, id);
        if (clientPreference != null && clientPreference.getIdClient() != null) {
            Client client = clientPreference.getIdClient();
            return client.getFirstName() + " " + client.getLastName();
        }
        return "Nombre no disponible";
     */

}
