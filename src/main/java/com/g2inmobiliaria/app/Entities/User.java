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
@Table(name = "tbUser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdUser", nullable = false)
    private Integer id;

    @Column(name = "UserName", length = 100)
    private String userName;

    @Column(name = "RegistrationDate")
    private LocalDate registrationDate;

    @Column(name = "Password", length = 100)
    private String password;

    @Column(name = "ActivationToken", length = 500)
    private String activationToken;

    @Column(name = "VerificationToken", length = 500)
    private String verificationToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdPerson")
    private Person idPerson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdRole")
    private Role idRole;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

    @OneToMany(mappedBy = "idUser")
    private Set<Client> tbClients = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idUser")
    private Set<Comment> tbComments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idUser")
    private Set<RealStateAgent> tbRealStateAgents = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idUser")
    private Set<Sesion> tbSesions = new LinkedHashSet<>();

}