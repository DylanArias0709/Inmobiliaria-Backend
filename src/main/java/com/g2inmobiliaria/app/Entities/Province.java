package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Importacion de anotación de LoomBook que provee los setters y getters de las entidades.
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbProvince")
public class Province {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdProvince")
    private Integer idProvince;

    @Column(name = "Name")
    private String name;

    @Column(name = "Status")
    private boolean status;
}
