package com.g2inmobiliaria.app.Repositories;

import com.g2inmobiliaria.app.Entities.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {

    @Procedure(name = "spCreateProvince")
    Integer spCreateProvince(
            @Param("Name") String name,
            @Param("Status") Short status,
            @Param("IdProvince") Integer idProvince
    );

}
