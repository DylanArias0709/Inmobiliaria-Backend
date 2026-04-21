package com.g2inmobiliaria.app.Services;

import com.g2inmobiliaria.app.Entities.PaymentMethod;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodService {

    @Autowired
    private EntityManager entityManager;

    public List<PaymentMethod> listarPaymentMethods() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spGetPaymentMethod", PaymentMethod.class);
        return query.getResultList();
    }

    public String registrarPaymentMethod(PaymentMethod paymentMethod) {
        // Crear la consulta de procedimiento almacenado con un parámetro
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spCreatePaymentMethod");
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);

        // Establecer el valor del parámetro
        query.setParameter(1, paymentMethod.getTypePaymentMethod());

        // Ejecutar el procedimiento almacenado
        query.execute();

        // Si deseas manejar algún tipo de salida o verificar algo, puedes hacerlo aquí
        return "{\"success\": true, \"message\": \"¡Método de pago creado exitosamente!\"}";
    }


    public String actualizarPaymentMethod(PaymentMethod paymentMethod) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spUpdatePaymentMethod");
        query.registerStoredProcedureParameter("IdPaymentMethod", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("TypePaymentMethod", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Status", Short.class, ParameterMode.IN);

        query.setParameter("IdPaymentMethod", paymentMethod.getId());
        query.setParameter("TypePaymentMethod", paymentMethod.getTypePaymentMethod());
        query.setParameter("Status", paymentMethod.getStatus());

        query.execute();

        // Aquí podrías agregar alguna lógica para manejar el resultado si es necesario
        return "{\"success\": true, \"message\": \"¡Método de pago actualizado exitosamente!\"}";
    }


    public String borradoLogicoPaymentMethod(int id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spLogicalDeletePaymentMethod");
        query.registerStoredProcedureParameter("IdPaymentMethod", Integer.class, ParameterMode.IN);

        query.setParameter("IdPaymentMethod", id);

        query.execute();

        // Asumir que si no hay excepción, la operación fue exitosa
        return "{\"success\": true, \"message\": \"Método de pago deshabilitado exitosamente.\"}";
    }


    public PaymentMethod obtenerPaymentMethodPorId(int id) {
        for (PaymentMethod paymentMethod : listarPaymentMethods()) {
            if (id == paymentMethod.getId()) {
                return paymentMethod;
            }
        }
        return null;
    }
}
