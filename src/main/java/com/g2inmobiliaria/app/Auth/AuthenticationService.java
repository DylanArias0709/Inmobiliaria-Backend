package com.g2inmobiliaria.app.Auth;

import com.g2inmobiliaria.app.Entities.*;
import com.g2inmobiliaria.app.Repositories.*;
import com.g2inmobiliaria.app.Services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import  com.g2inmobiliaria.app.Entities.User;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private CantonRepository cantonRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private DirectionRepository directionRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        // Verificar si el email ya existe
        if (emailRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Este email ya está registrado en el sistema");
        }

        // Crear y guardar la entidad Email
        Email emailEntity = Email.builder()
                .email(request.getEmail())
                .status(true)
                .build();
        emailRepository.save(emailEntity);

        // Crear y guardar la entidad Phone
        Phone phoneEntity = Phone.builder()
                .phoneNumber(request.getPhoneNumber())
                .status(true)
                .build();
        phoneRepository.save(phoneEntity);

        // Crear y guardar las entidades de dirección
        Province provinceEntity = Province.builder()
                .name(request.getProvince())
                .status(true)
                .build();
        provinceRepository.save(provinceEntity);

        Canton cantonEntity = Canton.builder()
                .province(provinceEntity)
                .name(request.getCanton())
                .status(true)
                .build();
        cantonRepository.save(cantonEntity);

        District districtEntity = District.builder()
                .canton(cantonEntity)
                .name(request.getDistrict())
                .status(true)
                .build();
        districtRepository.save(districtEntity);

        Direction directionEntity = Direction.builder()
                .province(provinceEntity)
                .canton(cantonEntity)
                .district(districtEntity)
                .aditionalInformation(request.getAditionalInformation())
                .status(true)
                .build();
        directionRepository.save(directionEntity);

        // Crear la entidad Person y asociar las entidades Email, Phone y Direction
        Person personEntity = Person.builder()
                .name(request.getName())
                .firstSurname(request.getFirstSurname())
                .secondSurname(request.getSecondSurname())
                .idCard(request.getIdCard())
                .email(emailEntity)
                .phone(phoneEntity)
                .direction(directionEntity)
                .status(true)
                .build();
        personRepository.save(personEntity);

        // Obtener el rol desde el repositorio
        Role defaultRole = roleRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("El rol no existe"));

        // Crear la entidad User y asociar la entidad Person y Role
        User user = User.builder()
                .person(personEntity)
                .userName(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // Hashear la contraseña antes de almacenarla
                .role(defaultRole)
                .status(true)
                .build();
        userRepository.save(user);

        // Generar el token JWT
        String jwtToken = jwtService.generateToken(new HashMap<>(), user);

        // Crear y devolver la respuesta de autenticación
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByPersonEmailEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Generar el token JWT
        String jwtToken = jwtService.generateToken(new HashMap<>(), user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}