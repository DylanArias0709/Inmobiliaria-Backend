package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tbImage")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdImage", nullable = false)
    private Integer id;

    @Column(name = "Image")
    private byte[] image;

    @Column(name = "RegistrationDate")
    private LocalDate registrationDate;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

    @OneToMany(mappedBy = "idImage")
    private Set<Property> tbProperties = new LinkedHashSet<>();

}