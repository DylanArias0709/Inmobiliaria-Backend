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
@Table(name = "tbProperty")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdProperty", nullable = false)
    private Integer id;

    @Column(name = "IdRealStateAgent")
    private Integer idRealStateAgent;

    @Column(name = "IdPropertyDetail")
    private Integer idPropertyDetail;

    @Column(name = "Price", precision = 10, scale = 2)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdClient")
    private Client idClient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdImage")
    private Image idImage;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

    @OneToMany(mappedBy = "idProperty")
    private Set<Agreement> tbAgreements = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idProperty")
    private Set<PropertyDetail> tbPropertyDetails = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idProperty")
    private Set<PublicationProperty> tbPublicationProperties = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idProperty")
    private Set<Visit> tbVisits = new LinkedHashSet<>();

}