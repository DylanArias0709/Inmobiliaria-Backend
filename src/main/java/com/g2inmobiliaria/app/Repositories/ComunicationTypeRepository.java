package com.g2inmobiliaria.app.Repositories;

import com.g2inmobiliaria.app.Entities.ComunicationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComunicationTypeRepository extends JpaRepository<ComunicationType, Integer> {

    Optional<ComunicationType> findByNameComunicationType(String NameComunicationType);

}
