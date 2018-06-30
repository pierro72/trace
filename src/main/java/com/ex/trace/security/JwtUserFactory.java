package com.ex.trace.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.ex.trace.domaine.Authority;
import com.ex.trace.domaine.Utilisateur;

import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(Utilisateur utilisateur) {
        return new JwtUser(
                utilisateur.getId(),
                utilisateur.getUsername(),
                utilisateur.getFirstname(),
                utilisateur.getLastname(),
                utilisateur.getEmail(),
                utilisateur.getPassword(),
                mapToGrantedAuthorities(utilisateur.getAuthorities()),
                utilisateur.getEnabled(),
                utilisateur.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
                .collect(Collectors.toList());
    }
}
