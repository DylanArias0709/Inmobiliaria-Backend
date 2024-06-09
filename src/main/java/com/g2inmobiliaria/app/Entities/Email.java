package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "tbEmail")

public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdEmail")
    private Integer idEmail;

    @Column(name = "Email")
    private String email;

    @Column(name = "Status")
    private boolean status;
}
