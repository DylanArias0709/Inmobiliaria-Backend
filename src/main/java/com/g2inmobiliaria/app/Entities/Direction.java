package com.g2inmobiliaria.app.Entities;

import com.g2inmobiliaria.app.Entities.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.g2inmobiliaria.app.Entities.Province;
import com.g2inmobiliaria.app.Entities.Canton;
import com.g2inmobiliaria.app.Entities.District;

@Data //Importacion de anotación de LoomBook que provee los setters y getters de las entidades.
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tDirection")
public class Direction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdDirection")
    private Integer idDirection;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "IdProvince", referencedColumnName = "IdProvince")
    @Column(name = "IdProvince")
    private Province province;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "IdCanton", referencedColumnName = "IdCanton")
    @Column(name = "IdCanton")
    private Canton canton;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "IdDistrict", referencedColumnName = "IdDistrict")
    @Column(name = "IdDistrict")
    private District district;

    @Column(name = "AditionalInformation")
    private String aditionalInformation;

    @Column(name = "Status")
    private boolean status;

}
