package com.forumhub_api.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.forumhub_api.service.TokenService;

import java.io.IOException;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final TokenService tokenService;

    public JwtAuthenticationFilter(TokenService tokenService) {
        super(authenticationManager -> authenticationManager);
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            logger.warn("Token ausente ou com formato inválido");
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer ", "");
        if (tokenService.validarToken(token)) {
            String username = tokenService.getUsernameFromToken(token);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("Usuário autenticado: {}", username);
        } else {
            logger.warn("Token inválido");
        }

        chain.doFilter(request, response);
    }
}