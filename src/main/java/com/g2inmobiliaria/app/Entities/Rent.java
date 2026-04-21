package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "tbRent")
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdRent", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdAgreement")
    private Agreement idAgreement;

    @Column(name = "RentPrice", precision = 10, scale = 2)
    private BigDecimal rentPrice;

    @Column(name = "StartDateRent")
    private LocalDate startDateRent;

    @Column(name = "EndDateRent")
    private LocalDate endDateRent;

    @Column(name = "MonthDuration")
    private Integer monthDuration;

    @Column(name = "InitialDeposit", precision = 10, scale = 2)
    private BigDecimal initialDeposit;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

}