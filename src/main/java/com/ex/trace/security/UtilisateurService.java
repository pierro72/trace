package com.ex.trace.security;

import com.ex.trace.repository.TraceRepository;
import com.ex.trace.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UtilisateurService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;


    public UtilisateurService( UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository    = utilisateurRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Objects.requireNonNull(email);
        return utilisateurRepository.findUserWithName(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


}