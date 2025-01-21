package com.forumhub_api.controller;

import com.forumhub_api.dto.LoginRequest;
import com.forumhub_api.exception.AuthException;
import com.forumhub_api.service.TokenService;
import com.forumhub_api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService service;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public Map<String, String> login(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.senha()));

            User user = (User) authentication.getPrincipal();
            String token = tokenService.gerarToken(user.getUsername());
            return Map.of("token", token, "tipo", "Bearer");
        } catch (AuthenticationException e) {
            throw new AuthException("Erro de autenticação: " + e.getMessage());
        }
    }

}