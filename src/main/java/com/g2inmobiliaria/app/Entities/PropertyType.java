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

    public PropertyType() {
        id = 0;
        propertyTypeName = "";
        propertyTypeDescription = "";
        status = 0;
    }

    public PropertyType(Integer id, String propertyTypeName, String propertyTypeDescription, Short status) {
        this.id = id;
        this.propertyTypeName = propertyTypeName;
        this.propertyTypeDescription = propertyTypeDescription;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPropertyTypeName() {
        return propertyTypeName;
    }

    public void setPropertyTypeName(String propertyTypeName) {
        this.propertyTypeName = propertyTypeName;
    }

    public String getPropertyTypeDescription() {
        return propertyTypeDescription;
    }

    public void setPropertyTypeDescription(String propertyTypeDescription) {
        this.propertyTypeDescription = propertyTypeDescription;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Set<PropertyDetail> getTbPropertyDetails() {
        return tbPropertyDetails;
    }

    public void setTbPropertyDetails(Set<PropertyDetail> tbPropertyDetails) {
        this.tbPropertyDetails = tbPropertyDetails;
    }
}