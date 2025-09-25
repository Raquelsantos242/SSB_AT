package com.br.infnet.service;

import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    public String generateToken(Authentication authentication) {
        System.out.println("=== GERANDO TOKEN ===");
        System.out.println("Usuário: " + authentication.getName());
        return "token-simulado";
    }
}
