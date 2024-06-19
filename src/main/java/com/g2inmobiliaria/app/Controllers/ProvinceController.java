/*package com.g2inmobiliaria.app.Controllers;

import com.g2inmobiliaria.app.Entities.Province;
import com.g2inmobiliaria.app.Services.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/provinces")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;

    @GetMapping
    public List<Province> getAllProvinces() {
        return provinceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Province> getProvinceById(@PathVariable Integer id) {
        Optional<Province> province = provinceService.findById(id);
        return province.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Province> createProvince(@RequestBody Province province) {
        int newProvinceId = provinceService.createProvince(province.getName(), province.getStatus());
        province.setId(newProvinceId);
        return ResponseEntity.ok(province);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Province> updateProvince(@PathVariable Integer id, @RequestBody Province provinceDetails) {
        Optional<Province> province = provinceService.findById(id);
        if (province.isPresent()) {
            Province updatedProvince = province.get();
            updatedProvince.setName(provinceDetails.getName());
            updatedProvince.setStatus(provinceDetails.getStatus());
            return ResponseEntity.ok(provinceService.save(updatedProvince));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvince(@PathVariable Integer id) {
        provinceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


 */