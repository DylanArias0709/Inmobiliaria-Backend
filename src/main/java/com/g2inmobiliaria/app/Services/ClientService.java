package com.g2inmobiliaria.app.Services;

import com.g2inmobiliaria.app.Entities.Client;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
@Service
public class ClientService {
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public List<Object[]> getClients(Integer idClient) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spGetClient");

        // Configurar los parámetros del procedimiento almacenado
        query.registerStoredProcedureParameter("IdClient", Integer.class, ParameterMode.IN);
        query.setParameter("IdClient", idClient);

        // Ejecutar el procedimiento almacenado y obtener los resultados como un objeto List<Object[]>
        List<Object[]> results = query.getResultList();

        return results;
    }

    @Transactional
    public String registerClient(Client client, String phoneNumber, String email) {
        try {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spRegisterPerson");
            query.registerStoredProcedureParameter("Name", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("FirstSurname", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("SecondSurname", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("IdCard", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("IdProvince", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("IdCanton", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("IdDistrict", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("AditionalInformation", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("Email", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("PhoneNumber", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("UserName", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("Password", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("ActivationToken", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("VerificationToken", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("Budget", BigDecimal.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("MaximumBudget", BigDecimal.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("StatusClient", Short.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("IdRole", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("PersonType", String.class, ParameterMode.IN);
            query.setParameter("Name", client.getIdUser().getIdPerson().getName());
            query.setParameter("FirstSurname", client.getIdUser().getIdPerson().getFirstSurname());
            query.setParameter("SecondSurname", client.getIdUser().getIdPerson().getSecondSurname());
            query.setParameter("IdCard", client.getIdUser().getIdPerson().getIdCard());
            query.setParameter("IdProvince", client.getIdUser().getIdPerson().getIdDirection().getIdDistrict().getIdCanton().getIdProvince().getId());
            query.setParameter("IdCanton", client.getIdUser().getIdPerson().getIdDirection().getIdDistrict().getIdCanton().getId());
            query.setParameter("IdDistrict", client.getIdUser().getIdPerson().getIdDirection().getIdDistrict().getId());
            query.setParameter("AditionalInformation", client.getIdUser().getIdPerson().getIdDirection().getAditionalInformation());
            query.setParameter("Email", email);
            query.setParameter("PhoneNumber", phoneNumber);
            query.setParameter("UserName", client.getIdUser().getUserName());
            query.setParameter("Password", client.getIdUser().getPassword());
            query.setParameter("ActivationToken", client.getIdUser().getActivationToken());
            query.setParameter("VerificationToken", client.getIdUser().getVerificationToken());
            query.setParameter("Budget", client.getBudget());
            query.setParameter("MaximumBudget", null); // Agente no necesita MaximumBudget
            query.setParameter("StatusClient", null); // Agente no necesita StatusClient
            query.setParameter("IdRole", client.getIdUser().getIdRole().getId());
            query.setParameter("PersonType", "C"); // Cliente por defecto
            query.execute();

            return "Cliente registrado exitosamente.";
        } catch (Exception ex) {
            ex.printStackTrace(); // Manejo de error según tu lógica
            return "Error al registrar cliente: " + ex.getMessage();
        }
    }

        @Transactional
        public String updateClient(Client client, String phoneNumber, String email) {

            try {
                StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spUpdateClient");

                query.registerStoredProcedureParameter("ClientId", Integer.class, ParameterMode.IN);
                query.registerStoredProcedureParameter("Name", String.class, ParameterMode.IN);
                query.registerStoredProcedureParameter("FirstSurname", String.class, ParameterMode.IN);
                query.registerStoredProcedureParameter("SecondSurname", String.class, ParameterMode.IN);
                query.registerStoredProcedureParameter("IdCard", String.class, ParameterMode.IN);
                query.registerStoredProcedureParameter("IdProvince", Integer.class, ParameterMode.IN);
                query.registerStoredProcedureParameter("IdCanton", Integer.class, ParameterMode.IN);
                query.registerStoredProcedureParameter("IdDistrict", Integer.class, ParameterMode.IN);
                query.registerStoredProcedureParameter("AditionalInformation", String.class, ParameterMode.IN);
                query.registerStoredProcedureParameter("Email", String.class, ParameterMode.IN);
                query.registerStoredProcedureParameter("PhoneNumber", String.class, ParameterMode.IN);
                query.registerStoredProcedureParameter("UserName", String.class, ParameterMode.IN);
                query.registerStoredProcedureParameter("Password", String.class, ParameterMode.IN);
                query.registerStoredProcedureParameter("ActivationToken", String.class, ParameterMode.IN);
                query.registerStoredProcedureParameter("VerificationToken", String.class, ParameterMode.IN);
                query.registerStoredProcedureParameter("Budget", BigDecimal.class, ParameterMode.IN);
                query.registerStoredProcedureParameter("IdRole", Integer.class, ParameterMode.IN);

                query.setParameter("ClientId", client.getId());
                query.setParameter("Name", client.getIdUser().getIdPerson().getName());
                query.setParameter("FirstSurname", client.getIdUser().getIdPerson().getFirstSurname());
                query.setParameter("SecondSurname", client.getIdUser().getIdPerson().getSecondSurname());
                query.setParameter("IdCard", client.getIdUser().getIdPerson().getIdCard());
                query.setParameter("IdProvince", client.getIdUser().getIdPerson().getIdDirection().getIdDistrict().getIdCanton().getIdProvince().getId());
                query.setParameter("IdCanton", client.getIdUser().getIdPerson().getIdDirection().getIdDistrict().getIdCanton().getId());
                query.setParameter("IdDistrict", client.getIdUser().getIdPerson().getIdDirection().getIdDistrict().getId());
                query.setParameter("AditionalInformation", client.getIdUser().getIdPerson().getIdDirection().getAditionalInformation());
                query.setParameter("Email", email);
                query.setParameter("PhoneNumber", phoneNumber);
                query.setParameter("UserName", client.getIdUser().getUserName());
                query.setParameter("Password", client.getIdUser().getPassword());
                query.setParameter("ActivationToken", client.getIdUser().getActivationToken());
                query.setParameter("VerificationToken", client.getIdUser().getVerificationToken());
                query.setParameter("Budget", client.getBudget());
                query.setParameter("IdRole", client.getIdUser().getIdRole().getId());

                query.execute();

                return "Client updated successfully.";
            } catch (Exception e) {
                return "Error updating client: " + e.getMessage();
            }
        }
}
