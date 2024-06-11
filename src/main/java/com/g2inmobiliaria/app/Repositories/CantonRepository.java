package com.g2inmobiliaria.app.Repositories;

import com.g2inmobiliaria.app.Entities.Canton;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CantonRepository extends JpaRepository<Canton, Integer>{
    Optional<Canton> findByName(String name);
}
