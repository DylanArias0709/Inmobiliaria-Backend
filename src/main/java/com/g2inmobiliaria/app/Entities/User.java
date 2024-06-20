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

    public User() {
        id = 0;
        userName = "";
        registrationDate = LocalDate.now();
        password = "";
        activationToken = "";
        verificationToken = "";
        idPerson = new Person();
        idRole = new Role();
    }

    public User(Integer id, Set<Sesion> tbSesions, Set<RealStateAgent> tbRealStateAgents, Set<Comment> tbComments, Set<Client> tbClients, Short status, Role idRole, Person idPerson, String verificationToken, String activationToken, String password, LocalDate registrationDate, String userName) {
        this.id = id;
        this.tbSesions = tbSesions;
        this.tbRealStateAgents = tbRealStateAgents;
        this.tbComments = tbComments;
        this.tbClients = tbClients;
        this.status = status;
        this.idRole = idRole;
        this.idPerson = idPerson;
        this.verificationToken = verificationToken;
        this.activationToken = activationToken;
        this.password = password;
        this.registrationDate = registrationDate;
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Sesion> getTbSesions() {
        return tbSesions;
    }

    public void setTbSesions(Set<Sesion> tbSesions) {
        this.tbSesions = tbSesions;
    }

    public Set<RealStateAgent> getTbRealStateAgents() {
        return tbRealStateAgents;
    }

    public void setTbRealStateAgents(Set<RealStateAgent> tbRealStateAgents) {
        this.tbRealStateAgents = tbRealStateAgents;
    }

    public Set<Comment> getTbComments() {
        return tbComments;
    }

    public void setTbComments(Set<Comment> tbComments) {
        this.tbComments = tbComments;
    }

    public Set<Client> getTbClients() {
        return tbClients;
    }

    public void setTbClients(Set<Client> tbClients) {
        this.tbClients = tbClients;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Role getIdRole() {
        return idRole;
    }

    public void setIdRole(Role idRole) {
        this.idRole = idRole;
    }

    public Person getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Person idPerson) {
        this.idPerson = idPerson;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}