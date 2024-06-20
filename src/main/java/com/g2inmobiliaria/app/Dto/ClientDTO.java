package com.g2inmobiliaria.app.Dto;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
@SqlResultSetMapping(
        name = "ClientDTOMapping",
        entities = {
                @EntityResult(
                        entityClass = ClientDTO.class,
                        fields = {
                                @FieldResult(name = "idClient", column = "IdClient"),
                                @FieldResult(name = "budget", column = "Budget"),
                                @FieldResult(name = "idUser", column = "IdUser"),
                                @FieldResult(name = "status", column = "Status"),
                                @FieldResult(name = "userName", column = "UserName"),
                                @FieldResult(name = "registrationDate", column = "RegistrationDate"),
                                @FieldResult(name = "userStatus", column = "UserStatus"),
                                @FieldResult(name = "name", column = "Name"),
                                @FieldResult(name = "firstSurname", column = "FirstSurname"),
                                @FieldResult(name = "secondSurname", column = "SecondSurname"),
                                @FieldResult(name = "idCard", column = "IdCard"),
                                @FieldResult(name = "idDirection", column = "IdDirection"),
                                @FieldResult(name = "personStatus", column = "PersonStatus")
                        }
                )
        }
)

public class ClientDTO {
    @Column(name = "IdClient")
    private Integer idClient;
    @Column(name = "Budget")
    private BigDecimal budget;
    @Column(name = "IdUser")
    private Integer idUser;
    @Column(name = "Status")
    private Short status;
    @Column(name = "UserName")
    private String userName;
    @Column(name = "RegistrationDate")
    private Date registrationDate;
    @Column(name = "UserStatus")
    private Short userStatus;
    @Column(name = "Name")
    private String name;
    @Column(name = "FirstSurname")
    private String firstSurname;
    @Column(name = "SecondSurname")
    private String secondSurname;
    @Column(name = "IdCard")
    private String idCard;
    @Column(name = "IdDirection")
    private Integer idDirection;
    @Column(name = "IdDirection")
    private Short personStatus;

    public ClientDTO() {
    }

    // Getters y setters

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Short getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Short userStatus) {
        this.userStatus = userStatus;
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

    public String getSecondSurname() {
        return secondSurname;
    }

    public void setSecondSurname(String secondSurname) {
        this.secondSurname = secondSurname;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getIdDirection() {
        return idDirection;
    }

    public void setIdDirection(Integer idDirection) {
        this.idDirection = idDirection;
    }

    public Short getPersonStatus() {
        return personStatus;
    }

    public void setPersonStatus(Short personStatus) {
        this.personStatus = personStatus;
    }
}
