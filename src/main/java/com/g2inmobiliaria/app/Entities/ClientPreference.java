package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "tbClientPreference")
public class ClientPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdClientPreference", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdClient")
    private Client idClient;

    @Column(name = "MinimumPrice", precision = 10, scale = 2)
    private BigDecimal minimumPrice;

    @Column(name = "MaximumPrice", precision = 10, scale = 2)
    private BigDecimal maximumPrice;

    @Column(name = "AmountMinimunRooms")
    private Integer amountMinimunRooms;

    @Column(name = "AmountMaximumRooms")
    private Integer amountMaximumRooms;

    @Column(name = "AmountMinimumBathrooms")
    private Integer amountMinimumBathrooms;

    @Column(name = "AmountMaximumBathrooms")
    private Integer amountMaximumBathrooms;

    @Column(name = "MinimumArea")
    private Integer minimumArea;

    @Column(name = "MaximumArea")
    private Integer maximumArea;

    @Column(name = "MinimumYearConstruction")
    private Integer minimumYearConstruction;

    @Column(name = "MaximumYearConstruction")
    private Integer maximumYearConstruction;

    @Column(name = "AdicionalCharacteristics", length = 200)
    private String adicionalCharacteristics;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

}