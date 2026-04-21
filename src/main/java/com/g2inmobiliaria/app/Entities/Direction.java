package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tbDirection")
public class Direction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdDirection", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdDistrict")
    private District idDistrict;

    @Column(name = "AditionalInformation", length = 200)
    private String aditionalInformation;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

    @OneToMany(mappedBy = "idDirection")
    private Set<Person> tbPeople = new LinkedHashSet<>();

    public Direction() {
        id = 0;
        idDistrict = new District();
        aditionalInformation = "";
    }

    public Direction(Integer id, Set<Person> tbPeople, String aditionalInformation, Short status, District idDistrict) {
        this.id = id;
        this.tbPeople = tbPeople;
        this.aditionalInformation = aditionalInformation;
        this.status = status;
        this.idDistrict = idDistrict;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Person> getTbPeople() {
        return tbPeople;
    }

    public void setTbPeople(Set<Person> tbPeople) {
        this.tbPeople = tbPeople;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getAditionalInformation() {
        return aditionalInformation;
    }

    public void setAditionalInformation(String aditionalInformation) {
        this.aditionalInformation = aditionalInformation;
    }

    public District getIdDistrict() {
        return idDistrict;
    }

    public void setIdDistrict(District idDistrict) {
        this.idDistrict = idDistrict;
    }
}