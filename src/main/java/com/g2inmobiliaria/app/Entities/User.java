package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Sesion> tbSesions = new LinkedHashSet<>();

    public Collection<? extends GrantedAuthority> getRoles() {
        Set<GrantedAuthority> roles = new HashSet<>();

        // Agregar el rol principal del usuario
        roles.add(new SimpleGrantedAuthority("ROLE_" + idRole.getRoleName()
        ));

        // Agregar roles adicionales si es necesario
        switch (idRole.getRoleName()) {
            case "ADMIN":
                roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                break;
            case "CLIENT":
                roles.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
                break;
            case "REAL_STATE_AGENT":
                roles.add(new SimpleGrantedAuthority("ROLE_REAL_STATE_AGENT"));
                break;
            default:
                // Agregar un manejo para otros roles si es necesario
                break;
        }

        return roles;
    }

}