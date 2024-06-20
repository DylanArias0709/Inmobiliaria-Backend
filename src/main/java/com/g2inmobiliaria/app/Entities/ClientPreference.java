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


    public ClientPreference() {

    }

    public ClientPreference(Integer id, Client idClient, BigDecimal minimumPrice, BigDecimal maximumPrice, Integer amountMinimunRooms, Integer amountMaximumRooms, Integer amountMinimumBathrooms, Integer amountMaximumBathrooms, Integer minimumArea, Integer maximumArea, Integer minimumYearConstruction, Integer maximumYearConstruction, String adicionalCharacteristics, Short status) {
        this.id = id;
        this.idClient = idClient;
        this.minimumPrice = minimumPrice;
        this.maximumPrice = maximumPrice;
        this.amountMinimunRooms = amountMinimunRooms;
        this.amountMaximumRooms = amountMaximumRooms;
        this.amountMinimumBathrooms = amountMinimumBathrooms;
        this.amountMaximumBathrooms = amountMaximumBathrooms;
        this.minimumArea = minimumArea;
        this.maximumArea = maximumArea;
        this.minimumYearConstruction = minimumYearConstruction;
        this.maximumYearConstruction = maximumYearConstruction;
        this.adicionalCharacteristics = adicionalCharacteristics;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Client getIdClient() {
        return idClient;
    }

    public void setIdClient(Client idClient) {
        this.idClient = idClient;
    }

    public BigDecimal getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(BigDecimal minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public BigDecimal getMaximumPrice() {
        return maximumPrice;
    }

    public void setMaximumPrice(BigDecimal maximumPrice) {
        this.maximumPrice = maximumPrice;
    }

    public Integer getAmountMinimunRooms() {
        return amountMinimunRooms;
    }

    public void setAmountMinimunRooms(Integer amountMinimunRooms) {
        this.amountMinimunRooms = amountMinimunRooms;
    }

    public Integer getAmountMaximumRooms() {
        return amountMaximumRooms;
    }

    public void setAmountMaximumRooms(Integer amountMaximumRooms) {
        this.amountMaximumRooms = amountMaximumRooms;
    }

    public Integer getAmountMinimumBathrooms() {
        return amountMinimumBathrooms;
    }

    public void setAmountMinimumBathrooms(Integer amountMinimumBathrooms) {
        this.amountMinimumBathrooms = amountMinimumBathrooms;
    }

    public Integer getAmountMaximumBathrooms() {
        return amountMaximumBathrooms;
    }

    public void setAmountMaximumBathrooms(Integer amountMaximumBathrooms) {
        this.amountMaximumBathrooms = amountMaximumBathrooms;
    }

    public Integer getMinimumArea() {
        return minimumArea;
    }

    public void setMinimumArea(Integer minimumArea) {
        this.minimumArea = minimumArea;
    }

    public Integer getMaximumArea() {
        return maximumArea;
    }

    public void setMaximumArea(Integer maximumArea) {
        this.maximumArea = maximumArea;
    }

    public Integer getMinimumYearConstruction() {
        return minimumYearConstruction;
    }

    public void setMinimumYearConstruction(Integer minimumYearConstruction) {
        this.minimumYearConstruction = minimumYearConstruction;
    }

    public Integer getMaximumYearConstruction() {
        return maximumYearConstruction;
    }

    public void setMaximumYearConstruction(Integer maximumYearConstruction) {
        this.maximumYearConstruction = maximumYearConstruction;
    }

    public String getAdicionalCharacteristics() {
        return adicionalCharacteristics;
    }

    public void setAdicionalCharacteristics(String adicionalCharacteristics) {
        this.adicionalCharacteristics = adicionalCharacteristics;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
}