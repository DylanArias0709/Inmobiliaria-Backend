package com.g2inmobiliaria.app.Repositories;

import com.g2inmobiliaria.app.Entities.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {

    Optional<PaymentMethod> findByTypePaymentMethod(String typePaymentMethod);

}
