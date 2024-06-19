package com.g2inmobiliaria.app.Services;

import com.g2inmobiliaria.app.Entities.Province;
import com.g2inmobiliaria.app.Repositories.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProvinceService {

    @Autowired
    private ProvinceRepository provinceRepository;

    public Integer createProvince(String name) {
        return provinceRepository.spCreateProvince(name);
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
