package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.g2inmobiliaria.app.Entities.Canton;

@Data //Importacion de anotación de LoomBook que provee los setters y getters de las entidades.
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbDistrict")
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdDistrict")
    private Integer idDistrict;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "IdCanton", referencedColumnName = "IdCanton")
    @Column(name = "IdCanton")
    private Canton canton;

    @Column(name = "Name")
    private String name;

    @Column(name = "Status")
    private boolean status;
}
