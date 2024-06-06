package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbRole")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdRole")
    private int idRole;

    @Column(name = "RoleName")
    private String roleName;

    @Column(name = "Status")
    private boolean status;

    // Constructor para roles por defecto
    public Role(String roleName, boolean status) {
        this.roleName = roleName;
        this.status = status;
    }

    // Roles por defecto
    public static final Role ROLE_ADMIN = new Role("ROLE_ADMIN", true);
    public static final Role CLIENT = new Role("CLIENT", true);
    public static final Role REAL_ESTATE_AGENT = new Role("REAL_ESTATE_AGENT", true);
}
