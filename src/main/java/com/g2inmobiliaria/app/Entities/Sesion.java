package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbSesion")

public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdSesion")
    private Integer idSesion;

    @Column(name = "TokenSesion")
    private String tokenSesion;

    @Column(name = "RegistrationSesionDate")
    private String registrationSesionDate;

    @Column(name = "ActualizationSesionDate")
    private String actualizationSesionDate;

    @Column(name = "ExpirationSesionDate")
    private String expirationSesionDate;

    @Column(name = "IdUser")
    private Integer idUser;

    @Column(name = "Status")
    private String status;

}
