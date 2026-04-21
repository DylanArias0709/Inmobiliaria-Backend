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


    public PaymentMethod() {
        id = 0;
        typePaymentMethod = "";
        status = 0;
    }

    public PaymentMethod(Integer id, String typePaymentMethod, Short status) {
        this.id = id;
        this.typePaymentMethod = typePaymentMethod;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypePaymentMethod() {
        return typePaymentMethod;
    }

    public void setTypePaymentMethod(String typePaymentMethod) {
        this.typePaymentMethod = typePaymentMethod;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Set<Sale> getTbSales() {
        return tbSales;
    }

    public void setTbSales(Set<Sale> tbSales) {
        this.tbSales = tbSales;
    }
}