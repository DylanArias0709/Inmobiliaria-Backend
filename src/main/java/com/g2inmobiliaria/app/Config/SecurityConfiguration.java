package com.g2inmobiliaria.app.Config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;


    //El security filterChain es el responsable de configurar toda la seguridad HTTP de la APP.

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception { //Recibe todas las solicitudes HTTP.

        http
                .csrf()
                .disable()
                //-----------EndPoints que no necesitan uso del filtrado de solicitudes HTTP.
                .authorizeHttpRequests()
                .requestMatchers("") //URL o EndPoints que no requieren de autentificacion del JWT como: Crear usuario o Iniciar Sesion.
                .permitAll() //Para brindarle permisos a todas las solicitudes indicadas en la lista de requestMatchers.
                .anyRequest() //Todas las otras URL o EndPoints que no esten dentro de la lista de requestMatchers si requiren de la autentificacion.
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Una solicitud de sesion sin estado.
                .and()
                //Especificar a Spring Boot que autentificador se utilizara.
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}
