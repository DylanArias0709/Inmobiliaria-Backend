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
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spCreatePaymentMethod");
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, Short.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.OUT);

        query.setParameter(1, paymentMethod.getTypePaymentMethod());
        query.setParameter(2, paymentMethod.getStatus());

        query.execute();

        int idPaymentMethodCreated = (int) query.getOutputParameterValue(3);

        if (idPaymentMethodCreated > 0) {
            return "{\"success\": true, \"message\": \"¡Método de pago creado exitosamente!\"}";
        } else if (idPaymentMethodCreated == -1) {
            return "{\"success\": false, \"message\": \"¡El tipo de método de pago ya existe!\"}";
        } else {
            return "{\"success\": false, \"message\": \"¡Hubo un error creando el método de pago!\"}";
        }
    }

    public String actualizarPaymentMethod(PaymentMethod paymentMethod) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spUpdatePaymentMethod");
        query.registerStoredProcedureParameter("IdPaymentMethod", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("TypePaymentMethod", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Status", Short.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("ErrorCode", Integer.class, ParameterMode.OUT);

        query.setParameter("IdPaymentMethod", paymentMethod.getId());
        query.setParameter("TypePaymentMethod", paymentMethod.getTypePaymentMethod());
        query.setParameter("Status", paymentMethod.getStatus());

        query.execute();

        int errorCode = (int) query.getOutputParameterValue("ErrorCode");
        if (errorCode == 0) {
            return "{\"success\": true, \"message\": \"¡Método de pago actualizado exitosamente!\"}";
        } else {
            return "{\"success\": false, \"message\": \"¡Hubo un error actualizando el método de pago!\"}";
        }
    }

    public String borradoLogicoPaymentMethod(int id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spLogicalDeletePaymentMethod");
        query.registerStoredProcedureParameter("IdPaymentMethod", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("IdPaymentMethodDeleted", Integer.class, ParameterMode.OUT);

        query.setParameter("IdPaymentMethod", id);

        query.execute();

        int idPaymentMethodDeleted = (int) query.getOutputParameterValue("IdPaymentMethodDeleted");

        if (idPaymentMethodDeleted == id) {
            return "{\"success\": true, \"message\": \"Método de pago deshabilitado exitosamente.\"}";
        } else {
            return "{\"success\": false, \"message\": \"Hubo un error deshabilitando el método de pago.\"}";
        }
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
