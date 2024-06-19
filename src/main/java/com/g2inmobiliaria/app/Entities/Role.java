package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tbRole")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdRole", nullable = false)
    private Integer id;

    @Column(name = "RoleName", length = 100)
    private String roleName;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

    @OneToMany(mappedBy = "idRole")
    private Set<User> tbUsers = new LinkedHashSet<>();

    public Role() {
        id = 0;
        roleName = "";
        status = 0;
    }

    public Role(Integer id, String roleName, Short status, Set<User> tbUsers) {
        this.id = id;
        this.roleName = roleName;
        this.status = status;
        this.tbUsers = tbUsers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Set<User> getTbUsers() {
        return tbUsers;
    }

    public void setTbUsers(Set<User> tbUsers) {
        this.tbUsers = tbUsers;
    }
}