package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tbServiceType")
public class ServiceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdServiceType", nullable = false)
    private Integer id;

    @Column(name = "NameServiceType", length = 100)
    private String nameServiceType;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

    @OneToMany(mappedBy = "idServiceType")
    private Set<PropertyDetail> tbPropertyDetails = new LinkedHashSet<>();


    public ServiceType() {
        id = 0;
        nameServiceType = "";
        status = 0;
    }

    public ServiceType(Integer id, String nameServiceType, Short status) {
        this.id = id;
        this.nameServiceType = nameServiceType;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameServiceType() {
        return nameServiceType;
    }

    public void setNameServiceType(String nameServiceType) {
        this.nameServiceType = nameServiceType;
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