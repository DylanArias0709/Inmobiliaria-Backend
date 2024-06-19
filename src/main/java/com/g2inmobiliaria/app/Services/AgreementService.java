package com.g2inmobiliaria.app.Services;

import com.g2inmobiliaria.app.Entities.Agreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class AgreementService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Método para utilizar el sp de crear acuerdo
    public void createAgreement(Integer idProperty, Integer idClient, Integer idRealStateAgent, Date agreementDate, String aditionalInformation) {
        try {
            jdbcTemplate.execute((Connection connection) -> {
                CallableStatement callableStatement = connection.prepareCall("{call spCreateAgreement(?, ?, ?, ?, ?)}");
                callableStatement.setInt(1, idProperty);
                callableStatement.setInt(2, idClient);
                callableStatement.setInt(3, idRealStateAgent);
                callableStatement.setDate(4, agreementDate);
                callableStatement.setString(5, aditionalInformation);

                callableStatement.execute();
                return null;
            });
        } catch (Exception e) {
            // Extraer solo el mensaje de la excepción
            String errorMessage = e.getCause().getMessage();
            throw new RuntimeException("Error al crear el acuerdo: " + errorMessage);
        }
    }
    // Método para realizar un borrado con el sp de borrado lógico
    public void logicalDeleteAgreement(Integer idAgreement) {
        try {
            jdbcTemplate.execute((Connection connection) -> {
                CallableStatement callableStatement = connection.prepareCall("{call spLogicalDeleteAgreement(?)}");
                callableStatement.setInt(1, idAgreement);

                callableStatement.execute();
                return null;
            });
        } catch (Exception e) {
            // Extraer solo el mensaje de la excepción
            String errorMessage = e.getCause().getMessage();
            throw new RuntimeException("Error al eliminar el acuerdo: " + errorMessage);
        }
    }
    // Método para realizar una búsqueda (listado) de los acuerdos (se puede filtrar según datos dados, o listar todos)
    public List<Agreement> getAgreements(Integer idAgreement, Integer idProperty, Integer idClient, Integer idRealStateAgent, Short status) {
        return jdbcTemplate.execute((Connection connection) -> {
            CallableStatement callableStatement = connection.prepareCall("{call spGetAgreement(?, ?, ?, ?, ?)}");
            callableStatement.setObject(1, idAgreement, Types.INTEGER);
            callableStatement.setObject(2, idProperty, Types.INTEGER);
            callableStatement.setObject(3, idClient, Types.INTEGER);
            callableStatement.setObject(4, idRealStateAgent, Types.INTEGER);
            callableStatement.setObject(5, status, Types.TINYINT);

            boolean hasResults = callableStatement.execute();

            List<Agreement> agreements = new ArrayList<>();
            if (hasResults) {
                try (ResultSet resultSet = callableStatement.getResultSet()) {
                    while (resultSet.next()) {
                        Agreement agreement = new Agreement();
                        agreement.setId(resultSet.getInt("IdAgreement"));
                        agreement.setAgreementDate(resultSet.getDate("AgreementDate").toLocalDate());
                        agreement.setAditionalInformation(resultSet.getString("AditionalInformation"));
                        agreement.setStatus(resultSet.getShort("Status"));
                        agreements.add(agreement);
                    }
                }
            } else {
                throw new RuntimeException("No results found");
            }
            return agreements;
        });
    }
    // Método para actualizar un acuerdo, utilizando el sp de spUpdateAgreement
    public void updateAgreement(Integer idAgreement, Integer idProperty, Integer idClient, Integer idRealStateAgent, java.sql.Date agreementDate, String aditionalInformation, Short status) {
        try {
            jdbcTemplate.execute((Connection connection) -> {
                CallableStatement callableStatement = connection.prepareCall("{call spUpdateAgreement(?, ?, ?, ?, ?, ?, ?)}");
                callableStatement.setInt(1, idAgreement);
                callableStatement.setInt(2, idProperty);
                callableStatement.setInt(3, idClient);
                callableStatement.setInt(4, idRealStateAgent);
                callableStatement.setDate(5, agreementDate);
                callableStatement.setString(6, aditionalInformation);
                callableStatement.setShort(7, status);

                callableStatement.execute();
                return null;
            });
        } catch (Exception e) {
            // Extraer solo el mensaje de la excepción
            String errorMessage = e.getCause().getMessage();
            throw new RuntimeException("Error al actualizar el acuerdo: " + errorMessage);
        }
    }
}