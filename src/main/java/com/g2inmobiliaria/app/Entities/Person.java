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

}