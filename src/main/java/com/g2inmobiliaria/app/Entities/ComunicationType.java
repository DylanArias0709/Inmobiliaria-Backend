package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tbComunicationType")
public class ComunicationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdComunicationType", nullable = false)
    private Integer id;

    @Column(name = "NameComunicationType", length = 100)
    private String nameComunicationType;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

    @OneToMany(mappedBy = "idComunicationType")
    private Set<Comunication> tbComunications = new LinkedHashSet<>();


    public ComunicationType() {

        id = 0;
        nameComunicationType = "";
        status = 0;
    }

    public ComunicationType(Integer id, String nameComunicationType, Short status) {
        this.id = id;
        this.nameComunicationType = nameComunicationType;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameComunicationType() {
        return nameComunicationType;
    }

    public void setNameComunicationType(String nameComunicationType) {
        this.nameComunicationType = nameComunicationType;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Set<Comunication> getTbComunications() {
        return tbComunications;
    }

    public void setTbComunications(Set<Comunication> tbComunications) {
        this.tbComunications = tbComunications;
    }
}