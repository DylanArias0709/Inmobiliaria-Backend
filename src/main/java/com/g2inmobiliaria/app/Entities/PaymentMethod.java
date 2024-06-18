package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tbPaymentMethod")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPaymentMethod", nullable = false)
    private Integer id;

    @Column(name = "TypePaymentMethod", length = 100)
    private String typePaymentMethod;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

    @OneToMany(mappedBy = "idPaymentMethod")
    private Set<Sale> tbSales = new LinkedHashSet<>();

}