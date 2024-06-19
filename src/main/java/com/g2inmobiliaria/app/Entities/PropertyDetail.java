package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "tbPropertyDetail")
public class PropertyDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPropertyDetails", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdPropertyType")
    private PropertyType idPropertyType;

    @Column(name = "NumberOfFloors")
    private Integer numberOfFloors;

    @Column(name = "TerrainArea", precision = 10, scale = 2)
    private BigDecimal terrainArea;

    @Column(name = "BuiltArea", precision = 10, scale = 2)
    private BigDecimal builtArea;

    @Column(name = "AmountRooms")
    private Integer amountRooms;

    @Column(name = "AmountBathrooms")
    private Integer amountBathrooms;

    @Column(name = "YearBuilt")
    private LocalDate yearBuilt;

    @Column(name = "AditionalInformation", length = 200)
    private String aditionalInformation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdServiceType")
    private ServiceType idServiceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdProperty")
    private Property idProperty;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

}