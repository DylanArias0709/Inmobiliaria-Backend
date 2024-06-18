package com.g2inmobiliaria.app.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, Integer> {

    // Método para encontrar un email por su dirección
    Optional<Email> findByEmail(String email);
}
