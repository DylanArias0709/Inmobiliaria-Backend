package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tbProvince")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdProvince", nullable = false)
    private Integer id;

    @Column(name = "Name", length = 100)
    private String name;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

    @OneToMany(mappedBy = "idProvince")
    private Set<Canton> tbCantons = new LinkedHashSet<>();

    public Province() {
        id = 0;
        name = "";
        status = 0;
    }

    public Province(Integer id, String name, Short status, Set<Canton> tbCantons) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.tbCantons = tbCantons;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Set<Canton> getTbCantons() {
        return tbCantons;
    }

    public void setTbCantons(Set<Canton> tbCantons) {
        this.tbCantons = tbCantons;
    }
}