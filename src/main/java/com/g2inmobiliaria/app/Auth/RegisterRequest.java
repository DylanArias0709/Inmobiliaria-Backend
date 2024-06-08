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

    private String firstSurname; //Entidad:Person

    private String secondSurname; //Entidad:Person

    private String email; //Entidad:User

    private String password; //Entidad:User
}
