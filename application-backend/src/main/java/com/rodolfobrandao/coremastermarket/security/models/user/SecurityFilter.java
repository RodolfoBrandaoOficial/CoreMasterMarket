package com.rodolfobrandao.coremastermarket.security.models.user;

import com.rodolfobrandao.coremastermarket.security.repositories.UserRepository;
import com.rodolfobrandao.coremastermarket.security.config.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private static final Logger logger = Logger.getLogger(SecurityFilter.class.getName());

    @Autowired
    TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            var token = this.recoverToken(request);
            if (token != null) {
                var login = tokenService.validateToken(token);
                if (login != null) {
                    UserDetails user = userRepository.findByLogin(login);
                    if (user != null) {
                        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        logger.warning("User not found for login: " + login);
                    }
                } else {
                    logger.warning("Invalid token: " + token);
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error processing authentication", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing authentication: " + e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }
        return null;
    }
}
