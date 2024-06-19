package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbPhone")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPhone", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdPerson")
    private Person idPerson;

    @Column(name = "PhoneNumber", length = 20)
    private String phoneNumber;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

}