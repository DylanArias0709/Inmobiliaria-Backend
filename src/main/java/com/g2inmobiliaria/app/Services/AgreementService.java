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
    public String createAgreement(Agreement agreement) {
        try {
            jdbcTemplate.execute((Connection connection) -> {
                CallableStatement callableStatement = connection.prepareCall("{call spCreateAgreement(?, ?, ?, ?, ?)}");
                callableStatement.setInt(1, agreement.getIdProperty().getId());
                callableStatement.setInt(2, agreement.getIdClient().getId());
                callableStatement.setInt(3, agreement.getIdRealStateAgent().getId());
                callableStatement.setDate(4, Date.valueOf(agreement.getAgreementDate()));
                callableStatement.setString(5, agreement.getAditionalInformation());

                callableStatement.execute();
                return null;
            });
            return "{\"success\": true, \"message\": \"¡Acuerdo creado exitosamente!\"}";
        } catch (Exception e) {
            String errorMessage = (e.getCause() != null) ? e.getCause().getMessage() : e.getMessage();
            return "{\"success\": false, \"message\": \"Error al crear el acuerdo: " + errorMessage + "\"}";
        }
    }

    // Método para realizar un borrado con el sp de borrado lógico
    public String logicalDeleteAgreement(int idAgreement) {
        try {
            jdbcTemplate.execute((Connection connection) -> {
                CallableStatement callableStatement = connection.prepareCall("{call spLogicalDeleteAgreement(?)}");
                callableStatement.setInt(1, idAgreement);

                callableStatement.execute();
                return null;
            });
            return "{\"success\": true, \"message\": \"Acuerdo eliminado exitosamente.\"}";
        } catch (Exception e) {
            String errorMessage = (e.getCause() != null) ? e.getCause().getMessage() : e.getMessage();
            return "{\"success\": false, \"message\": \"Error al eliminar el acuerdo: " + errorMessage + "\"}";
        }
    }

    // Método para realizar una búsqueda (listado) de los acuerdos
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
                throw new RuntimeException("No se encontraron resultados");
            }
            return agreements;
        });
    }

    // Método para actualizar un acuerdo, utilizando el sp de spUpdateAgreement
    public String updateAgreement(Agreement agreement) {
        try {
            jdbcTemplate.execute((Connection connection) -> {
                CallableStatement callableStatement = connection.prepareCall("{call spUpdateAgreement(?, ?, ?, ?, ?, ?, ?)}");
                callableStatement.setInt(1, agreement.getId());
                callableStatement.setInt(2, agreement.getIdProperty().getId());
                callableStatement.setInt(3, agreement.getIdClient().getId());
                callableStatement.setInt(4, agreement.getIdRealStateAgent().getId());
                callableStatement.setDate(5, Date.valueOf(agreement.getAgreementDate()));
                callableStatement.setString(6, agreement.getAditionalInformation());
                callableStatement.setShort(7, agreement.getStatus());

                callableStatement.execute();
                return null;
            });
            return "{\"success\": true, \"message\": \"¡Acuerdo actualizado exitosamente!\"}";
        } catch (Exception e) {
            String errorMessage = (e.getCause() != null) ? e.getCause().getMessage() : e.getMessage();
            return "{\"success\": false, \"message\": \"Error al actualizar el acuerdo: " + errorMessage + "\"}";
        }
    }
}