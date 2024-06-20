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
@Table(name = "tbRealStateAgent")
public class RealStateAgent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdRealStateAgent", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdUser")
    private User idUser;

    @Column(name = "IdClientPreference")
    private Integer idClientPreference;

    @Column(name = "MaximumBudget", precision = 10, scale = 2)
    private BigDecimal maximumBudget;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

    @OneToMany(mappedBy = "idRealStateAgent")
    private Set<Agreement> tbAgreements = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idRealStateAgent")
    private Set<Comunication> tbComunications = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idRealStateAgent")
    private Set<PublicationProperty> tbPublicationProperties = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idRealStateAgent")
    private Set<Sale> tbSales = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idRealStateAgent")
    private Set<Visit> tbVisits = new LinkedHashSet<>();

    public RealStateAgent(Integer id, User idUser, Integer idClientPreference, BigDecimal maximumBudget, Short status, Set<Agreement> tbAgreements, Set<Comunication> tbComunications, Set<PublicationProperty> tbPublicationProperties, Set<Sale> tbSales, Set<Visit> tbVisits) {
        this.id = id;
        this.idUser = idUser;
        this.idClientPreference = idClientPreference;
        this.maximumBudget = maximumBudget;
        this.status = status;
        this.tbAgreements = tbAgreements;
        this.tbComunications = tbComunications;
        this.tbPublicationProperties = tbPublicationProperties;
        this.tbSales = tbSales;
        this.tbVisits = tbVisits;
    }

    public RealStateAgent() {
        this.id = 0;
        this.idUser = new User();
    }
}