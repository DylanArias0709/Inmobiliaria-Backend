/*package com.g2inmobiliaria.app.Repositories;

import com.g2inmobiliaria.app.Entities.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DirectionRepository extends JpaRepository<Direction, Integer> {
    Optional<Direction> findByProvince_IdProvince(Integer idProvince);
    Optional<Direction> findByCanton_IdCanton(Integer idCanton);
    Optional<Direction> findByDistrict_IdDistrict(Integer idDistrict);
    Optional<Direction> findByAditionalInformation(String additionalInformation);
}
*/