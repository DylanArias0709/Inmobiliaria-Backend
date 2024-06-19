package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "tbSesion")
public class Sesion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdSesion", nullable = false)
    private Integer id;

    @Column(name = "TokenSesion", length = 500)
    private String tokenSesion;

    @Column(name = "RegistrationSesionDate")
    private LocalDate registrationSesionDate;

    @Column(name = "ActualizationSesionDate")
    private LocalDate actualizationSesionDate;

    @Column(name = "ExpirationSesionDate")
    private LocalDate expirationSesionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdUser")
    private User idUser;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

}