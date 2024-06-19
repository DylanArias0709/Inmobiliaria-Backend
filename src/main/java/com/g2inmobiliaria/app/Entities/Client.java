package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tbClient")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdClient", nullable = false)
    private Integer id;

    @Column(name = "Budget", precision = 10, scale = 2)
    private BigDecimal budget;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdUser")
    private User idUser;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

    @OneToMany(mappedBy = "idClient")
    private Set<Agreement> tbAgreements = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idClient")
    private Set<ClientPreference> tbClientPreferences = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idClient")
    private Set<Comunication> tbComunications = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idClient")
    private Set<Property> tbProperties = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idClient")
    private Set<Sale> tbSales = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idClient")
    private Set<Visit> tbVisits = new LinkedHashSet<>();

}