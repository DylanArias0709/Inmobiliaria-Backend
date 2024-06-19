/*package com.g2inmobiliaria.app.Services;

import com.g2inmobiliaria.app.Entities.Province;
import com.g2inmobiliaria.app.Repositories.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Service
public class ProvinceService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProvinceRepository provinceRepository;

    public int createProvince(String name, short status) {
        return jdbcTemplate.execute((connection) -> {
            CallableStatement callableStatement = connection.prepareCall("{call spCreateProvince(?, ?, ?)}");
            callableStatement.setString(1, name);
            callableStatement.setShort(2, status);
            callableStatement.registerOutParameter(3, Types.INTEGER);

            callableStatement.execute();
            return callableStatement.getInt(3);
        });
    }

    public List<Province> findAll() {
        return provinceRepository.findAll();
    }

    public Optional<Province> findById(Integer id) {
        return provinceRepository.findById(id);
    }

    public Province save(Province province) {
        return provinceRepository.save(province);
    }

    public void deleteById(Integer id) {
        provinceRepository.deleteById(id);
    }
}


 */