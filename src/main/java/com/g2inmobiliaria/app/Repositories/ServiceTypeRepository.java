package com.g2inmobiliaria.app.Repositories;

import com.g2inmobiliaria.app.Entities.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Integer> {

    Optional<ServiceType> findByNameServiceType(String nameServiceType);

}
