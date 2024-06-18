package com.g2inmobiliaria.app.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Integer>{
    Optional<District> findDistrictByName(String name);
}
