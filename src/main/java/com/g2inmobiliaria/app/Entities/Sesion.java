package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbSesion")
public class Sesion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdSesion", nullable = false)
    private Integer id;

    @Column(name = "PRIMARY_ID", nullable = false, length = 36)
    private String primaryId;

    @Column(name = "SESSION_ID", nullable = false, length = 36)
    private String sessionId;

    @Column(name = "CREATION_TIME", nullable = false)
    private Long creationTime;

    @Column(name = "LAST_ACCESS_TIME", nullable = false)
    private Long lastAccessTime;

    @Column(name = "EXPIRY_TIME", nullable = false)
    private Long expiryTime;

    @Column(name = "MAX_INACTIVE_INTERVAL", nullable = false)
    private Integer maxInactiveInterval;

    @Column(name = "PRINCIPAL_NAME", nullable = false, length = 100)
    private String principalName;

    @Column(name = "SESSION_ATTRIBUTES")
    private byte[] sessionAttributes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdUser", nullable = false)
    private User user;
}