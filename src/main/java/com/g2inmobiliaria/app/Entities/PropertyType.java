package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tbPropertyType")
public class PropertyType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPropertyType", nullable = false)
    private Integer id;

    @Column(name = "PropertyTypeName", length = 100)
    private String propertyTypeName;

    @Column(name = "PropertyTypeDescription", length = 100)
    private String propertyTypeDescription;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

    @OneToMany(mappedBy = "idPropertyType")
    private Set<PropertyDetail> tbPropertyDetails = new LinkedHashSet<>();

}