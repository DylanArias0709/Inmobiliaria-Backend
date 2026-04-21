package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tbPerson")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPerson", nullable = false)
    private Integer id;

    @Column(name = "Name", length = 100)
    private String name;

    @Column(name = "FirstSurname", length = 100)
    private String firstSurname;

    @Column(name = "SecondSurname", length = 100)
    private String secondSurname;

    @Column(name = "IdCard", length = 100)
    private String idCard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdDirection")
    private Direction idDirection;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

    @OneToMany(mappedBy = "idPerson")
    private Set<Email> tbEmails = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idPerson")
    private Set<Phone> tbPhones = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idPerson")
    private Set<User> tbUsers = new LinkedHashSet<>();

    public Person() {
        id = 0;
        name = "";
        firstSurname = "";
        secondSurname = "";
        idCard = "";
        idDirection = new Direction();
    }

    public Person(Integer id, Set<User> tbUsers, Set<Phone> tbPhones, Set<Email> tbEmails, Short status, Direction idDirection, String idCard, String secondSurname, String firstSurname, String name) {
        this.id = id;
        this.tbUsers = tbUsers;
        this.tbPhones = tbPhones;
        this.tbEmails = tbEmails;
        this.status = status;
        this.idDirection = idDirection;
        this.idCard = idCard;
        this.secondSurname = secondSurname;
        this.firstSurname = firstSurname;
        this.name = name;
    }

    public Set<User> getTbUsers() {
        return tbUsers;
    }

    public void setTbUsers(Set<User> tbUsers) {
        this.tbUsers = tbUsers;
    }

    public Set<Phone> getTbPhones() {
        return tbPhones;
    }

    public void setTbPhones(Set<Phone> tbPhones) {
        this.tbPhones = tbPhones;
    }

    public Set<Email> getTbEmails() {
        return tbEmails;
    }

    public void setTbEmails(Set<Email> tbEmails) {
        this.tbEmails = tbEmails;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Direction getIdDirection() {
        return idDirection;
    }

    public void setIdDirection(Direction idDirection) {
        this.idDirection = idDirection;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getSecondSurname() {
        return secondSurname;
    }

    public void setSecondSurname(String secondSurname) {
        this.secondSurname = secondSurname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstSurname() {
        return firstSurname;
    }

    public void setFirstSurname(String firstSurname) {
        this.firstSurname = firstSurname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}