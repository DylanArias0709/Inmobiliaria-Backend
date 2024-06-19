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

}