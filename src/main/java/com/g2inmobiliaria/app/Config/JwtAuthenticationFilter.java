/*package com.g2inmobiliaria.app.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.g2inmobiliaria.app.Services.JwtService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
//Clase para filtrar las solicitudes HTTP que recibe el sistema para verificar si traen el JWT Token correcto, para dar permisos.
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtService jwtService;
    private final UserDetailsService userDetailsService; //Interface proveniente de dependencia: Spring Security.


    //Método para realizar filtro de solicitud Token JWT por cada solicitud HTTP realizada en el sistema.
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization"); //Encabezado de autorizacion del JWT Token.
        final String jwtToken; //Token JWT que se envia.
        final String userEmail;

        //--------Validacion para que cada solicitud HTTPS del sistema verificar que posea un JWT Token.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) { //Bearer: Contenido del JWT Token.
            filterChain.doFilter(request, response); //Pasar la solicitud HTTPS al siguiente filtro.
            return;
        }
        //Extraer la información del Token JWT.
        jwtToken = authHeader.substring(7); //Se inicializa en 7 debido a que: "Bearer " posee 7 espacios.
        //Después de obtener el contenido del Token Jwt, obtenemos el correo del usuario mediante el Token.
        userEmail = jwtService.extractUserEmail(jwtToken);
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) { //Verificar que el usuario NO esta autenticado. Para no volver a realizar validaciones.

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            //------------Validar si el token sigue estando valido y no ha expirado.----------------//
            if(jwtService.isTokenValid(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                //Una vez ya creada la informacion del Token Jwt, se brindan mas detalles.
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request) //Para poder crear los detalles a partir de la solicitud HTTP.
                );
                SecurityContextHolder.getContext().setAuthentication(authToken); //Actualizar el token de autentificacion.
            }
        }
        filterChain.doFilter(request, response); //Hacer que la solicitud HTTPS pase por el filtro de autentificacion.
    }
}


 */