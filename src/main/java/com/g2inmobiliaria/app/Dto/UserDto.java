package com.g2inmobiliaria.app.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserDto {
    private String provinceName;
    private String cantonName;
    private String districtName;
    private String aditionalInformation; //Direccion
    private String phoneNumber;
    private String email;
    private String name;
    private String firstSurname;
    private String secondSurname;
    private Integer idCard;
    private String userName;
    private String password;
    private String activationToken;
    private String verificationToken;
    private String roleName;
}
