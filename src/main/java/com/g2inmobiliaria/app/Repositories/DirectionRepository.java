package com.g2inmobiliaria.app.Repositories;

import com.g2inmobiliaria.app.Entities.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DirectionRepository extends JpaRepository<Direction, Integer>{
    Optional<Direction> findByProvinceId(int idProvince);
    Optional<Direction> findByCantonId(int idCanton);
    Optional<Direction> findByDistrictId(int idDistrict);
    Optional<Direction> findByAditionalInformation(String aditionalInformation);
}
