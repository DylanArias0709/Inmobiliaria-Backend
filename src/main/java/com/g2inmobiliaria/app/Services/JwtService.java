package com.g2inmobiliaria.app.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE3MTc2NjMyODYsImV4cCI6MTc0OTE5OTI4NiwiYXVkIjoid3d3LmV4YW1wbGUuY29tIiwic3ViIjoianJvY2tldEBleGFtcGxlLmNvbSIsIkdpdmVuTmFtZSI6IkpvaG5ueSIsIlN1cm5hbWUiOiJSb2NrZXQiLCJFbWFpbCI6Impyb2NrZXRAZXhhbXBsZS5jb20iLCJSb2xlIjpbIk1hbmFnZXIiLCJQcm9qZWN0IEFkbWluaXN0cmF0b3IiXX0.JZk4_C9TPeHw3uVNUEeK9I33HiFDGjkxC0sTeFsx0lVL76jad97OMT76T-iASDpT_c0bAOgrpbZ6Pl-2vYOcog";


    public String extractUserEmail(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject); //Mediante el Token Jwt: Obtenemos los reclamos y accedemos al emisor(Subject) del Jwt Token.
    }

    //Metodo para permitir extraer el único reclamo del Jwt Token que aprobó el sistema.
    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {

        final Claims claims = extractAllClaims(jwtToken); //Obtener un solo reclamo del Token Jwt .
        return claimsResolver.apply(claims); //Obtener la lista de reclamos del Token Jwt .
    }

    //Metodo para crear un token sin necesidad de reclamos = Generar un token unicamente a partir de los detalles del usuario.
    public String generateTokenWithDetailsUser(UserDetails userDetails) {

        return generateToken(new HashMap<>(), userDetails);
    }


    //Obtener reclamos del Jwt Token.
    private Claims extractAllClaims(String jwtToken) {

        return Jwts
                .parser()
                .setSigningKey(getSignInKey()) //La firma digital la cual se encargara de validar que el remitente del Jwt Token es quien dice ser mediante la informacionm enviada en el Jwt Token y que no haya cambiado la informacion durante la solicitud HTTPS.
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private Key getSignInKey() {

        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    //Metodo para generar un Token Jwt.
    public String generateToken(
            Map<String, Object> extraClaims, //Dentro de extraClaims esta el objeto User.
            UserDetails userDetails //Utilizamos UserDetails para poder obtener mediante el Token Jwt informacion como el correo del emitente del Token para validar sus credenciales.
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis())) //Para obtener la fecha de creacion del Token Jwt: La cual se tomara en el momento que se genera.
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) //El Token JWT tendra una expiracion de 1 hora.
                .signWith(getSignInKey(), SignatureAlgorithm.HS512) //Se indica un hash en 512 bit ya que el token de referencia SECRET_KEY es en 512 bit.
                .compact();

    }

    //Metodo para validar el Token Jwt.
    public boolean isTokenValid(String token, UserDetails userDetails) { //Necesitamos la informacion del Token Jwt y la informacion del Usuario: Para poder validar que el Token le pertenece a ese Usuario. - Filtro de seguridad.

        final String userEmail = extractUserEmail(token);

        return (userEmail.equals(userDetails.getUsername())) && !isTokenExpired(token); //getUsername es de obtener correo. --Nomenclatura propia de manejar username SpringBoot
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }
}