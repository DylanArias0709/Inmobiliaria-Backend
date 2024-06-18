package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tbDistrict")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdDistrict", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdCanton")
    private Canton idCanton;

    @Column(name = "Name", length = 200)
    private String name;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

    @OneToMany(mappedBy = "idDistrict")
    private Set<Direction> tbDirections = new LinkedHashSet<>();

}