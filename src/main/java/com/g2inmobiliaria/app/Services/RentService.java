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

    // Método para crear Rent
    public String createRent(Rent rent) {
        try {
            jdbcTemplate.execute((Connection connection) -> {
                CallableStatement callableStatement = connection.prepareCall("{call spCreateRent(?, ?, ?, ?, ?, ?)}");
                callableStatement.setInt(1, rent.getIdAgreement().getId());
                callableStatement.setBigDecimal(2, rent.getRentPrice());
                callableStatement.setDate(3, Date.valueOf(rent.getStartDateRent()));
                callableStatement.setDate(4, Date.valueOf(rent.getEndDateRent()));
                callableStatement.setInt(5, rent.getMonthDuration());
                callableStatement.setBigDecimal(6, rent.getInitialDeposit());

                callableStatement.execute();
                return null;
            });
            return "{\"success\": true, \"message\": \"¡Renta creada exitosamente!\"}";
        } catch (Exception e) {
            String errorMessage = (e.getCause() != null) ? e.getCause().getMessage() : e.getMessage();
            return "{\"success\": false, \"message\": \"Error al crear la renta: " + errorMessage + "\"}";
        }
    }

    // Método para borrado lógico de Rent
    public String logicalDeleteRent(int idRent) {
        try {
            jdbcTemplate.execute((Connection connection) -> {
                CallableStatement callableStatement = connection.prepareCall("{call spLogicalDeleteRent(?)}");
                callableStatement.setInt(1, idRent);

                callableStatement.execute();
                return null;
            });
            return "{\"success\": true, \"message\": \"Renta deshabilitada exitosamente.\"}";
        } catch (Exception e) {
            String errorMessage = (e.getCause() != null) ? e.getCause().getMessage() : e.getMessage();
            return "{\"success\": false, \"message\": \"Error al eliminar la renta: " + errorMessage + "\"}";
        }
    }

    // Método para listar Rentas
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

                        int agreementId = resultSet.getInt("IdAgreement");
                        if (!resultSet.wasNull()) {
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

    // Método para actualizar Rent
    public String updateRent(Rent rent) {
        try {
            jdbcTemplate.execute((Connection connection) -> {
                CallableStatement callableStatement = connection.prepareCall("{call spUpdateRent(?, ?, ?, ?, ?, ?, ?, ?)}");
                callableStatement.setInt(1, rent.getId());
                callableStatement.setInt(2, rent.getIdAgreement().getId());
                callableStatement.setBigDecimal(3, rent.getRentPrice());
                callableStatement.setDate(4, Date.valueOf(rent.getStartDateRent()));
                callableStatement.setDate(5, Date.valueOf(rent.getEndDateRent()));
                callableStatement.setInt(6, rent.getMonthDuration());
                callableStatement.setBigDecimal(7, rent.getInitialDeposit());
                callableStatement.setShort(8, rent.getStatus());

                callableStatement.execute();
                return null;
            });
            return "{\"success\": true, \"message\": \"¡Renta actualizada exitosamente!\"}";
        } catch (Exception e) {
            String errorMessage = (e.getCause() != null) ? e.getCause().getMessage() : e.getMessage();
            return "{\"success\": false, \"message\": \"Error al actualizar la renta: " + errorMessage + "\"}";
        }
    }
}