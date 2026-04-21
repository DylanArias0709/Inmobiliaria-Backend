package com.g2inmobiliaria.app.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    // Datos de la entidad Direction
    private String province;
    private String canton;
    private String district;
    private String aditionalInformation;

    // Datos de la entidad Email
    private String email;

    // Datos de la entidad Phone
    private String phoneNumber;

    // Datos de la entidad Person
    private String name;
    private String firstSurname;
    private String secondSurname;
    private Integer idCard;


    // Datos de la entidad User
    private String password;
}
