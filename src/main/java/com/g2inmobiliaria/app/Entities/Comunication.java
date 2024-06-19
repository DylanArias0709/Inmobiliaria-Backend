package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "tbComunication")
public class Comunication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdComunication", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdComunicationType")
    private ComunicationType idComunicationType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IdClient", nullable = false)
    private Client idClient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IdRealStateAgent", nullable = false)
    private RealStateAgent idRealStateAgent;

    @Column(name = "DateTimeComunication")
    private Instant dateTimeComunication;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

}