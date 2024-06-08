package com.g2inmobiliaria.app.Auth;

import com.g2inmobiliaria.app.Entities.*;
import com.g2inmobiliaria.app.Repositories.EmailRepository;
import com.g2inmobiliaria.app.Repositories.PersonRepository;
import com.g2inmobiliaria.app.Repositories.RoleRepository;
import com.g2inmobiliaria.app.Repositories.UserRepository;
import com.g2inmobiliaria.app.Services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import  com.g2inmobiliaria.app.Entities.User;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository; //Para poder hacer uso del repositorio.

    private final PasswordEncoder passwordEncoder;
    private final EmailRepository emailRepository;
    private final PersonRepository personRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;


    public AuthenticationResponse register(RegisterRequest request) {
        // Verificar si el email ya existe
        if (emailRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Este email ya está registrado en el sistema");
        }

        // Crear la entidad Email
        Email emailEntity = Email.builder()
                .email(request.getEmail())
                .status(true)
                .build();

        // Crear la entidad Person y asociar la entidad Email
        Person personEntity = Person.builder()
                .firstSurname(request.getFirstSurname())
                .secondSurname(request.getSecondSurname())
                .email(emailEntity)
                .status(true)
                .build();

        // Obtener el rol desde el repositorio
        Role defaultRole = roleRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("El rol no existe"));

        // Crear la entidad User y asociar la entidad Person y Role
        User user = User.builder()
                .person(personEntity)
                .password(passwordEncoder.encode(request.getPassword())) // Hashear la contraseña antes de almacenarla
                .role(defaultRole)
                .status(true)
                .build();

        // Guardar la entidad User (con cascada guardará Person y Email)
        userRepository.save(user);

        // Generar el token JWT
        String jwtToken = jwtService.generateToken(new HashMap<>(), user);

        // Crear y devolver la respuesta de autenticación
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        return null;
    }
}
