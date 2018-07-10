package com.ex.trace.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.ex.trace.domaine.security.Authority;
import com.ex.trace.domaine.security.Utilisateur;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUtilisateurFactory {

    private JwtUtilisateurFactory() {
    }

    public static JwtUtilisateur create(Utilisateur utilisateur) {
        return new JwtUtilisateur(
                utilisateur.getId(),
                utilisateur.getUsername(),
                utilisateur.getEmail(),
                utilisateur.getPassword(),
                mapToGrantedAuthorities(utilisateur.getAuthorities()),
                utilisateur.getEnabled(),
                utilisateur.getLastPasswordResetDate()
        );
    }

    private static Collection<? extends GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());
    }
}
