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

    // Campo para almacenar el nombre de la provincia
    @Column(name = "name", length = 200)
    private String provinceName;

    @OneToMany(mappedBy = "idCanton")
    private Set<District> tbDistricts = new LinkedHashSet<>();

    public Canton() {
        id = 0;
        idProvince = new Province();
        name = "";
        status = 0;
        provinceName = "";
    }

    public Canton(Integer id, String provinceName, Set<District> tbDistricts, Short status, Province idProvince, String name) {
        this.id = id;
        this.provinceName = provinceName;
        this.tbDistricts = tbDistricts;
        this.status = status;
        this.idProvince = idProvince;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<District> getTbDistricts() {
        return tbDistricts;
    }

    public void setTbDistricts(Set<District> tbDistricts) {
        this.tbDistricts = tbDistricts;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
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
}