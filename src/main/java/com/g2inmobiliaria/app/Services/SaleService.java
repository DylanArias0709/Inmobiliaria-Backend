package com.g2inmobiliaria.app.Services;

import com.g2inmobiliaria.app.Entities.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Método para crear sale con sp
    public void createSale(Integer idAgreement, Integer idClient, Integer idRealStateAgent, java.sql.Date saleDate, String aditionalInformation, Integer idPaymentMethod) {
        try {
            jdbcTemplate.execute((Connection connection) -> {
                CallableStatement callableStatement = connection.prepareCall("{call spCreateSale(?, ?, ?, ?, ?, ?)}");
                callableStatement.setInt(1, idAgreement);
                callableStatement.setInt(2, idClient);
                callableStatement.setInt(3, idRealStateAgent);
                callableStatement.setDate(4, saleDate);
                callableStatement.setString(5, aditionalInformation);
                callableStatement.setInt(6, idPaymentMethod);

                callableStatement.execute();
                return null;
            });
        } catch (Exception e) {
            String errorMessage = (e.getCause() != null) ? e.getCause().getMessage() : e.getMessage();
            throw new RuntimeException("Error al crear la venta: " + errorMessage);
        }
    }
    // Método de borrado lógico con sp
    public void logicalDeleteSale(Integer idSale) {
        try {
            jdbcTemplate.execute((Connection connection) -> {
                CallableStatement callableStatement = connection.prepareCall("{call spLogicalDeleteSale(?)}");
                callableStatement.setInt(1, idSale);

                callableStatement.execute();
                return null;
            });
        } catch (Exception e) {
            String errorMessage = (e.getCause() != null) ? e.getCause().getMessage() : e.getMessage();
            throw new RuntimeException("Error al eliminar la venta: " + errorMessage);
        }
    }
    // Método de listar con sp
    public List<Sale> getSales(Integer idSale, Integer idAgreement, Integer idClient, Integer idRealStateAgent, Integer idPaymentMethod, Short status) {
        return jdbcTemplate.execute((Connection connection) -> {
            CallableStatement callableStatement = connection.prepareCall("{call spGetSale(?, ?, ?, ?, ?, ?)}");
            callableStatement.setObject(1, idSale, Types.INTEGER);
            callableStatement.setObject(2, idAgreement, Types.INTEGER);
            callableStatement.setObject(3, idClient, Types.INTEGER);
            callableStatement.setObject(4, idRealStateAgent, Types.INTEGER);
            callableStatement.setObject(5, idPaymentMethod, Types.INTEGER);
            callableStatement.setObject(6, status, Types.TINYINT);

            boolean hasResults = callableStatement.execute();

            List<Sale> sales = new ArrayList<>();
            if (hasResults) {
                try (ResultSet resultSet = callableStatement.getResultSet()) {
                    while (resultSet.next()) {
                        Sale sale = new Sale();
                        sale.setId(resultSet.getInt("IdSale"));
                        sale.setSaleDate(resultSet.getDate("SaleDate").toLocalDate());
                        sale.setAditionalInformation(resultSet.getString("AditionalInformation"));
                        sale.setStatus(resultSet.getShort("Status"));
                        // Map other fields and relationships if needed
                        sales.add(sale);
                    }
                }
            } else {
                throw new RuntimeException("No se encontraron resultados");
            }
            return sales;
        });
    }
    // Método para actualizar sale con sp
    public void updateSale(Integer idSale, Integer idAgreement, Integer idClient, Integer idRealStateAgent, java.sql.Date saleDate, String aditionalInformation, Integer idPaymentMethod, Short status) {
        try {
            jdbcTemplate.execute((Connection connection) -> {
                CallableStatement callableStatement = connection.prepareCall("{call spUpdateSale(?, ?, ?, ?, ?, ?, ?, ?)}");
                callableStatement.setInt(1, idSale);
                callableStatement.setInt(2, idAgreement);
                callableStatement.setInt(3, idClient);
                callableStatement.setInt(4, idRealStateAgent);
                callableStatement.setDate(5, saleDate);
                callableStatement.setString(6, aditionalInformation);
                callableStatement.setInt(7, idPaymentMethod);
                callableStatement.setShort(8, status);

                callableStatement.execute();
                return null;
            });
        } catch (Exception e) {
            String errorMessage = (e.getCause() != null) ? e.getCause().getMessage() : e.getMessage();
            throw new RuntimeException("Error al actualizar la venta: " + errorMessage);
        }
    }
}