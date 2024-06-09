package com.g2inmobiliaria.app.Repositories;

import com.g2inmobiliaria.app.Entities.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface PhoneRepository extends JpaRepository <Phone, Integer> {
    Optional<Phone> findByPhoneNumber(String phoneNumber);
}
