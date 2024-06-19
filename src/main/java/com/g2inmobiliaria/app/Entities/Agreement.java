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
@Table(name = "tbAgreement")
public class Agreement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdAgreement", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdProperty")
    private Property idProperty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdClient")
    private Client idClient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdRealStateAgent")
    private RealStateAgent idRealStateAgent;

    @Column(name = "AgreementDate")
    private LocalDate agreementDate;

    @Column(name = "AditionalInformation", length = 200)
    private String aditionalInformation;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

    @OneToMany(mappedBy = "idAgreement")
    private Set<Rent> tbRents = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idAgreement")
    private Set<Sale> tbSales = new LinkedHashSet<>();

}