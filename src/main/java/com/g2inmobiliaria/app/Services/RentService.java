package com.g2inmobiliaria.app.Services;

import com.g2inmobiliaria.app.Entities.Agreement;
import com.g2inmobiliaria.app.Entities.Rent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class RentService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AgreementService agreementService;

    // Método para utilizar el sp de crear Rent
    public void createRent(Integer idAgreement, java.math.BigDecimal rentPrice, java.sql.Date startDateRent, java.sql.Date endDateRent, Integer monthDuration, java.math.BigDecimal initialDeposit) {
        try {
            jdbcTemplate.execute((Connection connection) -> {
                CallableStatement callableStatement = connection.prepareCall("{call spCreateRent(?, ?, ?, ?, ?, ?)}");
                callableStatement.setInt(1, idAgreement);
                callableStatement.setBigDecimal(2, rentPrice);
                callableStatement.setDate(3, startDateRent);
                callableStatement.setDate(4, endDateRent);
                callableStatement.setInt(5, monthDuration);
                callableStatement.setBigDecimal(6, initialDeposit);

                callableStatement.execute();
                return null;
            });
        } catch (Exception e) {
            // Extraer solo el mensaje de la excepción
            String errorMessage = e.getCause().getMessage();
            throw new RuntimeException("Error al crear el alquiler: " + errorMessage);
        }
    }
    // Método para utilizar el sp de borrado lógico
    public void logicalDeleteRent(Integer idRent) {
        try {
            jdbcTemplate.execute((Connection connection) -> {
                CallableStatement callableStatement = connection.prepareCall("{call spLogicalDeleteRent(?)}");
                callableStatement.setInt(1, idRent);

                callableStatement.execute();
                return null;
            });
        } catch (Exception e) {
            // Extraer solo el mensaje de la excepción
            String errorMessage = e.getCause().getMessage();
            throw new RuntimeException("Error al eliminar el alquiler: " + errorMessage);
        }
    }
    // Método para utilizar el sp de listar rentas
    // Método para utilizar el sp de listar rentas
    public List<Rent> getRents(Integer idRent, Integer idAgreement, Short status) {
        return jdbcTemplate.execute((Connection connection) -> {
            CallableStatement callableStatement = connection.prepareCall("{call spGetRent(?, ?, ?)}");
            callableStatement.setObject(1, idRent, Types.INTEGER);
            callableStatement.setObject(2, idAgreement, Types.INTEGER);
            callableStatement.setObject(3, status, Types.TINYINT);

            boolean hasResults = callableStatement.execute();

            List<Rent> rents = new ArrayList<>();
            if (hasResults) {
                try (ResultSet resultSet = callableStatement.getResultSet()) {
                    while (resultSet.next()) {
                        Rent rent = new Rent();
                        rent.setId(resultSet.getInt("IdRent"));
                        rent.setRentPrice(resultSet.getBigDecimal("RentPrice"));
                        rent.setStartDateRent(resultSet.getDate("StartDateRent").toLocalDate());
                        rent.setEndDateRent(resultSet.getDate("EndDateRent").toLocalDate());
                        rent.setMonthDuration(resultSet.getInt("MonthDuration"));
                        rent.setInitialDeposit(resultSet.getBigDecimal("InitialDeposit"));
                        rent.setStatus(resultSet.getShort("Status"));

                        // Obtener idAgreement
                        int agreementId = resultSet.getInt("IdAgreement");
                        if (!resultSet.wasNull()) {
                            // Usar el método getAgreements para obtener el Agreement
                            List<Agreement> agreements = agreementService.getAgreements(agreementId, null, null, null, null);
                            if (!agreements.isEmpty()) {
                                rent.setIdAgreement(agreements.get(0));
                            }
                        }

                        rents.add(rent);
                    }
                }
            } else {
                throw new RuntimeException("No results found");
            }
            return rents;
        });
    }

    // Método para utilizar sp de modificar rent
    public void updateRent(Integer idRent, Integer idAgreement, java.math.BigDecimal rentPrice, java.sql.Date startDateRent, java.sql.Date endDateRent, Integer monthDuration, java.math.BigDecimal initialDeposit, Short status) {
        try {
            jdbcTemplate.execute((Connection connection) -> {
                CallableStatement callableStatement = connection.prepareCall("{call spUpdateRent(?, ?, ?, ?, ?, ?, ?, ?)}");
                callableStatement.setInt(1, idRent);
                callableStatement.setInt(2, idAgreement);
                callableStatement.setBigDecimal(3, rentPrice);
                callableStatement.setDate(4, startDateRent);
                callableStatement.setDate(5, endDateRent);
                callableStatement.setInt(6, monthDuration);
                callableStatement.setBigDecimal(7, initialDeposit);
                callableStatement.setShort(8, status);

                callableStatement.execute();
                return null;
            });
        } catch (Exception e) {
            String errorMessage = (e.getCause() != null) ? e.getCause().getMessage() : e.getMessage();
            throw new RuntimeException("Error al actualizar el alquiler: " + errorMessage);
        }
    }
}