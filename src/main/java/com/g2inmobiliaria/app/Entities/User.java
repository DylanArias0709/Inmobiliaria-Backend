package com.g2inmobiliaria.app.Entities;

import java.util.ArrayList;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data //Importacion de anotación de LoomBook que provee los setters y getters de las entidades.
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "tbUser")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Para hacer que cada registro tenga su ID autoincrementable.
    @Column(name = "IdUser")
    private Integer idUser;

    @Column(name = "UserName")
    private String userName;

    @Column(name = "RegistrationDate")
    private Date registrationDate;

    @Column(name = "Password")
    private String password;

    @Column(name = "ActivationToken")
    private String activationToken;

    @Column(name = "VerificationToken")
    private String verificationToken;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "IdPerson", referencedColumnName = "IdPerson")
    private Person person;

    //Obtener el roll del usuario.
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "IdRole", referencedColumnName = "IdRole")
    private Role role;

    @Column(name = "Status")
    private boolean status;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Crear una lista de los roles del usuario
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getRoleName())); // Agregar el rol del usuario

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    /* --Metodo por default.
    @Override
    public String getUsername() {
        return person.getEmail().getEmail();
    }
    */
    @Override
    public String getUsername() {
        if (person != null && person.getEmail() != null) {
            return person.getEmail().getEmail();
        } else {
            return null;
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
