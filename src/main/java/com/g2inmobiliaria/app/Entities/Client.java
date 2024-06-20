package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity

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

    public Client(Integer id, Set<Visit> tbVisits, Set<Property> tbProperties, Set<Sale> tbSales, Set<Comunication> tbComunications, Set<ClientPreference> tbClientPreferences, Set<Agreement> tbAgreements, Short status, User idUser, BigDecimal budget) {
        this.id = id;
        this.tbVisits = tbVisits;
        this.tbProperties = tbProperties;
        this.tbSales = tbSales;
        this.tbComunications = tbComunications;
        this.tbClientPreferences = tbClientPreferences;
        this.tbAgreements = tbAgreements;
        this.status = status;
        this.idUser = idUser;
        this.budget = budget;
    }

    public Client() {
        id = 0;
        budget = BigDecimal.valueOf(0);
        idUser= new User();
    }

    public Set<Visit> getTbVisits() {
        return tbVisits;
    }

    public void setTbVisits(Set<Visit> tbVisits) {
        this.tbVisits = tbVisits;
    }

    public Set<Sale> getTbSales() {
        return tbSales;
    }

    public void setTbSales(Set<Sale> tbSales) {
        this.tbSales = tbSales;
    }

    public Set<Property> getTbProperties() {
        return tbProperties;
    }

    public void setTbProperties(Set<Property> tbProperties) {
        this.tbProperties = tbProperties;
    }

    public Set<Comunication> getTbComunications() {
        return tbComunications;
    }

    public void setTbComunications(Set<Comunication> tbComunications) {
        this.tbComunications = tbComunications;
    }

    public Set<ClientPreference> getTbClientPreferences() {
        return tbClientPreferences;
    }

    public void setTbClientPreferences(Set<ClientPreference> tbClientPreferences) {
        this.tbClientPreferences = tbClientPreferences;
    }

    public Set<Agreement> getTbAgreements() {
        return tbAgreements;
    }

    public void setTbAgreements(Set<Agreement> tbAgreements) {
        this.tbAgreements = tbAgreements;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}