package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tbProvince")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdProvince", nullable = false)
    private Integer id;

    @Column(name = "Name", length = 100)
    private String name;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

    @OneToMany(mappedBy = "idProvince")
    private Set<Canton> tbCantons = new LinkedHashSet<>();

}