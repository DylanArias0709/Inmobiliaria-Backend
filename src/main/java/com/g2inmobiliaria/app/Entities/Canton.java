package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.g2inmobiliaria.app.Entities.Province;

@Data //Importacion de anotación de LoomBook que provee los setters y getters de las entidades.
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbCanton")
public class Canton {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdCanton")
    private Integer idCanton;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "IdProvince", referencedColumnName = "IdProvince")
    @Column(name = "IdProvince")
    private Province province;

    @Column(name = "Name")
    private String name;

    @Column(name = "Status")
    private boolean status;

}
