package com.g2inmobiliaria.app.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhoneRepository extends JpaRepository <Phone, Integer> {
    Optional<Phone> findByPhoneNumber(String phoneNumber);
}
