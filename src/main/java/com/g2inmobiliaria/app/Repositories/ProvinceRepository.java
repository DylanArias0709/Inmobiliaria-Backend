package com.g2inmobiliaria.app.Repositories;

import com.g2inmobiliaria.app.Entities.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ProvinceRepository extends JpaRepository<Province, Integer> {

    Optional<Province> findByName(String Name);

}
