package com.rodolfobrandao.coremastermarket.security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.rodolfobrandao.coremastermarket.security.models.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;


@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getUsername())
                    .withClaim("userId", user.getId()) // Usando o método getId() para obter o ID do usuário
                    .withExpiresAt(Date.from(genExpirationDate()))
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException | UnsupportedEncodingException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }


    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return "";
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    // Novo método para extrair o ID do usuário do token
    public String getUserIdFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getClaim("userId").asString(); // Alterado para asInt() se o ID do usuário for um número
        } catch (JWTVerificationException | UnsupportedEncodingException exception){
            throw new RuntimeException("Invalid token", exception);
        }
    }


    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
