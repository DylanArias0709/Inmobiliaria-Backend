package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tbVisit")
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdVisit", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdProperty")
    private Property idProperty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdRealStateAgent")
    private RealStateAgent idRealStateAgent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdClient")
    private Client idClient;

    @Column(name = "IdCalification")
    private Integer idCalification;

    @Column(name = "VisitDate")
    private LocalDate visitDate;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

    @OneToMany(mappedBy = "idVisit")
    private Set<Comment> tbComments = new LinkedHashSet<>();

}