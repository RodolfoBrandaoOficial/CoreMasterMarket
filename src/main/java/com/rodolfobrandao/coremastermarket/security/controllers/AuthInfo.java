package com.rodolfobrandao.coremastermarket.security.controllers;

import com.rodolfobrandao.coremastermarket.security.config.TokenService;
import com.rodolfobrandao.coremastermarket.security.models.user.User;
import com.rodolfobrandao.coremastermarket.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authinfo")
@CrossOrigin(origins = "*")
public class AuthInfo {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @GetMapping("/userinfo")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            // Verifica se o cabeçalho de autorização não está vazio e começa com "Bearer "
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Token de autenticação inválido");
            }

            // Extrai o token JWT do cabeçalho
            String jwt = authorizationHeader.substring(7); // Remove "Bearer " prefix

            // Extrai o ID do usuário do token JWT usando o serviço de token
            String userId = tokenService.getUserIdFromToken(jwt);

            // Busca o usuário correspondente no banco de dados usando o ID
            User user = repository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            // Retorna os detalhes do usuário na resposta
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            // Em caso de erro, retorna uma resposta de erro com status 401 (Unauthorized)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Erro ao obter informações do usuário: " + e.getMessage());
        }
    }
}
