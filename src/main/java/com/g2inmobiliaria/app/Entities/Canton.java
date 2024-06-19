package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tbCanton")
public class Canton {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdCanton", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdProvince")
    private Province idProvince;

    @Column(name = "Name", length = 200)
    private String name;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

    @OneToMany(mappedBy = "idCanton")
    private Set<District> tbDistricts = new LinkedHashSet<>();

    public Canton() {
    }

    public Canton(Integer id, Province idProvince, String name, Short status, Set<District> tbDistricts) {
        this.id = id;
        this.idProvince = idProvince;
        this.name = name;
        this.status = status;
        this.tbDistricts = tbDistricts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Province getIdProvince() {
        return idProvince;
    }

    public void setIdProvince(Province idProvince) {
        this.idProvince = idProvince;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Set<District> getTbDistricts() {
        return tbDistricts;
    }

    public void setTbDistricts(Set<District> tbDistricts) {
        this.tbDistricts = tbDistricts;
    }
}