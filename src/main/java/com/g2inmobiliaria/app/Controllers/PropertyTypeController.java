package com.g2inmobiliaria.app.Controllers;

import com.g2inmobiliaria.app.Entities.PropertyType;
import com.g2inmobiliaria.app.Services.PropertyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/property-types")
public class PropertyTypeController {

    @Autowired
    private PropertyTypeService propertyTypeService;

    @GetMapping("/list")
    public String listPropertyTypes(Model model) {
        List<PropertyType> propertyTypes = propertyTypeService.listPropertyTypes();
        model.addAttribute("propertyTypes", propertyTypes);
        return "property_types/property_types_admin"; // Ajusta la vista según tu estructura
    }

    @PostMapping("/createPropertyTypes")
    public ResponseEntity<?> createPropertyType(@RequestBody PropertyType propertyType) {
        return ResponseEntity.ok().body(propertyTypeService.createPropertyType(propertyType));
    }

    @PostMapping("/updatePropertyTypes")
    public ResponseEntity<?> updatePropertyType(@RequestBody PropertyType propertyType) {
        return ResponseEntity.ok().body(propertyTypeService.updatePropertyType(propertyType));
    }

    @DeleteMapping("/deletePropertyTypes")
    public ResponseEntity<?> deletePropertyType(@RequestParam("propertyType") int id) {
        return ResponseEntity.ok().body(propertyTypeService.logicalDeletePropertyType(id));
    }

    @GetMapping("/formPropertyTypes")
    public String showForm(@RequestParam("PropertyType") Integer id, Model model) {
        PropertyType propertyType = propertyTypeService.getPropertyTypeById(id);
        model.addAttribute("propertyType", propertyType);
        return "property_types/property_type_form"; // Ajusta la vista según tu estructura
    }
}
