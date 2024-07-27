package com.rodolfobrandao.coremastermarket.security.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodolfobrandao.coremastermarket.security.repositories.UserRepository;
import com.rodolfobrandao.coremastermarket.security.config.TokenService;
import com.rodolfobrandao.coremastermarket.security.models.user.AutheticationDTO;
import com.rodolfobrandao.coremastermarket.security.models.user.LoginResponseDTO;
import com.rodolfobrandao.coremastermarket.security.models.user.RegisterDTO;
import com.rodolfobrandao.coremastermarket.security.models.user.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private ObjectMapper objectMapper;
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AutheticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if (data.password() == null || data.password().isEmpty()) {
            return ResponseEntity.badRequest().body("A senha não pode ser nula ou vazia");
        }

        if (this.repository.findByLogin(data.login()) != null) {
            return ResponseEntity.badRequest().body("Usuário já existe");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role());
        this.repository.save(newUser);
        return ResponseEntity.ok().body("Usuário criado com sucesso");
    }

    @GetMapping("/userinfo")
    public ResponseEntity<String> getUserInfo(@RequestHeader("Authorization") String authorizationHeader) {
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
            return ResponseEntity.ok(String.valueOf(user)); // Retorna os detalhes do usuário como JSON
        } catch (Exception e) {
            // Em caso de erro, retorna uma resposta de erro com status 401 (Unauthorized)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Erro ao obter informações do usuário: " + e.getMessage());
        }
    }



}
