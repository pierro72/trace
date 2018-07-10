package com.ex.trace.security.repository;

import com.ex.trace.domaine.security.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by py
 */
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Utilisateur findByUsername(String username);

    Utilisateur findOneByEmail(String email);
}
