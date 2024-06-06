package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*; //Librería para el mapeo de entidades y la persistencia de datos.
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbPerson")

public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPerson")
    private Integer idPerson;

    @Column(name = "Name")
    private String name;

    @Column(name = "FirstSurname")
    private String firstSurname;

    @Column(name = "SecondSurname")
    private String secondSurname;

    @Column(name = "IdCard")
    private Integer idCard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdEmail", referencedColumnName = "IdEmail")
    private Email email;

    @Column(name = "IdPhone")
    private Integer idPhone;

    @Column(name = "IdDirection")
    private Integer idDirection;

    @Column(name = "Status")
    private boolean status;

}
