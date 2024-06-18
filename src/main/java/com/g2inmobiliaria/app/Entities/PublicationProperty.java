package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "tbPublicationProperty")
public class PublicationProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPublicationProperty", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdProperty")
    private Property idProperty;

    @Column(name = "PublicationDate")
    private LocalDate publicationDate;

    @Column(name = "ExpirationDate")
    private LocalDate expirationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdRealStateAgent")
    private RealStateAgent idRealStateAgent;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

}