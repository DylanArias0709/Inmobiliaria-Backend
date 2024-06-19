package com.g2inmobiliaria.app.Services;

import com.g2inmobiliaria.app.Entities.PaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentMethodService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Método para crear con el sp
    public void createPaymentMethod(String typePaymentMethod) {
        try {
            jdbcTemplate.execute((Connection connection) -> {
                CallableStatement callableStatement = connection.prepareCall("{call spCreatePaymentMethod(?)}");
                callableStatement.setString(1, typePaymentMethod);

                callableStatement.execute();
                return null;
            });
        } catch (Exception e) {
            String errorMessage = (e.getCause() != null) ? e.getCause().getMessage() : e.getMessage();
            throw new RuntimeException("Error al crear el método de pago: " + errorMessage);
        }
    }
    // Método para eliminar con el sp de borrado lógico
    public void logicalDeletePaymentMethod(Integer idPaymentMethod) {
        try {
            jdbcTemplate.execute((Connection connection) -> {
                CallableStatement callableStatement = connection.prepareCall("{call spLogicalDeletePaymentMethod(?)}");
                callableStatement.setInt(1, idPaymentMethod);

                callableStatement.execute();
                return null;
            });
        } catch (Exception e) {
            String errorMessage = (e.getCause() != null) ? e.getCause().getMessage() : e.getMessage();
            throw new RuntimeException("Error al eliminar el método de pago: " + errorMessage);
        }
    }
    // Método para modificar con el sp de actualizar
    public void updatePaymentMethod(Integer idPaymentMethod, String typePaymentMethod, Short status) {
        try {
            jdbcTemplate.execute((Connection connection) -> {
                CallableStatement callableStatement = connection.prepareCall("{call spUpdatePaymentMethod(?, ?, ?)}");
                callableStatement.setInt(1, idPaymentMethod);
                callableStatement.setString(2, typePaymentMethod);
                callableStatement.setShort(3, status);

                callableStatement.execute();
                return null;
            });
        } catch (Exception e) {
            String errorMessage = (e.getCause() != null) ? e.getCause().getMessage() : e.getMessage();
            throw new RuntimeException("Error al actualizar el método de pago: " + errorMessage);
        }
    }
    // Método para listar utilizando el sp
    public List<PaymentMethod> getPaymentMethods(Integer idPaymentMethod) {
        return jdbcTemplate.execute((Connection connection) -> {
            CallableStatement callableStatement = connection.prepareCall("{call spGetPaymentMethod(?)}");
            callableStatement.setObject(1, idPaymentMethod, Types.INTEGER);

            boolean hasResults = callableStatement.execute();

            List<PaymentMethod> paymentMethods = new ArrayList<>();
            if (hasResults) {
                try (ResultSet resultSet = callableStatement.getResultSet()) {
                    while (resultSet.next()) {
                        PaymentMethod paymentMethod = new PaymentMethod();
                        paymentMethod.setId(resultSet.getInt("IdPaymentMethod"));
                        paymentMethod.setTypePaymentMethod(resultSet.getString("TypePaymentMethod"));
                        paymentMethod.setStatus(resultSet.getShort("Status"));
                        paymentMethods.add(paymentMethod);
                    }
                }
            } else {
                throw new RuntimeException("No se encontraron resultados");
            }
            return paymentMethods;
        });
    }
}