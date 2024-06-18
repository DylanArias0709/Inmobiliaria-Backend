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

}