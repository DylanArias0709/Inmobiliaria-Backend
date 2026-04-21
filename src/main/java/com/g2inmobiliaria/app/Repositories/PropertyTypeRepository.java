package com.g2inmobiliaria.app.Repositories;


import com.g2inmobiliaria.app.Entities.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PropertyTypeRepository extends JpaRepository<PropertyType, Integer> {

    Optional<PropertyType> findByPropertyTypeName(String PropertyTypeName);

}
