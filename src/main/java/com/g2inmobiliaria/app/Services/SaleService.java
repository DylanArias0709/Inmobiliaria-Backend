package com.g2inmobiliaria.app.Services;

import com.g2inmobiliaria.app.Entities.Agreement;
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
    @Autowired
    private AgreementService agreementService;

    // Método para crear sale con sp
    public String createSale(Sale sale) {
        try {
            jdbcTemplate.execute((Connection connection) -> {
                CallableStatement callableStatement = connection.prepareCall("{call spCreateSale(?, ?, ?, ?, ?, ?)}");
                callableStatement.setInt(1, sale.getIdAgreement().getId());
                callableStatement.setInt(2, sale.getIdClient().getId());
                callableStatement.setInt(3, sale.getIdRealStateAgent().getId());
                callableStatement.setDate(4, Date.valueOf(sale.getSaleDate()));
                callableStatement.setString(5, sale.getAditionalInformation());
                callableStatement.setInt(6, sale.getIdPaymentMethod().getId());

                callableStatement.execute();
                return null;
            });
            return "{\"success\": true, \"message\": \"¡Venta creada exitosamente!\"}";
        } catch (Exception e) {
            String errorMessage = (e.getCause() != null) ? e.getCause().getMessage() : e.getMessage();
            return "{\"success\": false, \"message\": \"Error al crear la venta: " + errorMessage + "\"}";
        }
    }

    // Método de borrado lógico con sp
    public String logicalDeleteSale(int idSale) {
        try {
            jdbcTemplate.execute((Connection connection) -> {
                CallableStatement callableStatement = connection.prepareCall("{call spLogicalDeleteSale(?)}");
                callableStatement.setInt(1, idSale);

                callableStatement.execute();
                return null;
            });
            return "{\"success\": true, \"message\": \"Venta eliminada exitosamente.\"}";
        } catch (Exception e) {
            String errorMessage = (e.getCause() != null) ? e.getCause().getMessage() : e.getMessage();
            return "{\"success\": false, \"message\": \"Error al eliminar la venta: " + errorMessage + "\"}";
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

                        int agreementId = resultSet.getInt("IdAgreement");
                        if (!resultSet.wasNull()) {
                            List<Agreement> agreements = agreementService.getAgreements(agreementId, null, null, null, null);
                            if (!agreements.isEmpty()) {
                                sale.setIdAgreement(agreements.getFirst());
                            }
                        }

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
    public String updateSale(Sale sale) {
        try {
            jdbcTemplate.execute((Connection connection) -> {
                CallableStatement callableStatement = connection.prepareCall("{call spUpdateSale(?, ?, ?, ?, ?, ?, ?, ?)}");
                callableStatement.setInt(1, sale.getId());
                callableStatement.setInt(2, sale.getIdAgreement().getId());
                callableStatement.setInt(3, sale.getIdClient().getId());
                callableStatement.setInt(4, sale.getIdRealStateAgent().getId());
                callableStatement.setDate(5, Date.valueOf(sale.getSaleDate()));
                callableStatement.setString(6, sale.getAditionalInformation());
                callableStatement.setInt(7, sale.getIdPaymentMethod().getId());
                callableStatement.setShort(8, sale.getStatus());

                callableStatement.execute();
                return null;
            });
            return "{\"success\": true, \"message\": \"¡Venta actualizada exitosamente!\"}";
        } catch (Exception e) {
            String errorMessage = (e.getCause() != null) ? e.getCause().getMessage() : e.getMessage();
            return "{\"success\": false, \"message\": \"Error al actualizar la venta: " + errorMessage + "\"}";
        }
    }
}
