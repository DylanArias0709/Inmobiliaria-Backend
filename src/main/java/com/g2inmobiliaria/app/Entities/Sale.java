package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "tbSale")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdSale", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdAgreement")
    private Agreement idAgreement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdClient")
    private Client idClient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdRealStateAgent")
    private RealStateAgent idRealStateAgent;

    @Column(name = "SaleDate")
    private LocalDate saleDate;

    @Column(name = "AditionalInformation", length = 200)
    private String aditionalInformation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdPaymentMethod")
    private PaymentMethod idPaymentMethod;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

    public Sale(Integer id, Agreement idAgreement, Client idClient, RealStateAgent idRealStateAgent, LocalDate saleDate, String aditionalInformation, PaymentMethod idPaymentMethod, Short status) {
        this.id = id;
        this.idAgreement = idAgreement;
        this.idClient = idClient;
        this.idRealStateAgent = idRealStateAgent;
        this.saleDate = saleDate;
        this.aditionalInformation = aditionalInformation;
        this.idPaymentMethod = idPaymentMethod;
        this.status = status;
    }

    public Sale() {
        this.idClient = new Client();
        this.idRealStateAgent = new RealStateAgent();
    }
}