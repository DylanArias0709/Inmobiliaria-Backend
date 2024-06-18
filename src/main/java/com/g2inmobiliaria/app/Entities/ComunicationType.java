package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tbComunicationType")
public class ComunicationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdComunicationType", nullable = false)
    private Integer id;

    @Column(name = "NameComunicationType", length = 100)
    private String nameComunicationType;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

    @OneToMany(mappedBy = "idComunicationType")
    private Set<Comunication> tbComunications = new LinkedHashSet<>();

}