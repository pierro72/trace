package com.ex.trace.repository;

import com.ex.trace.domaine.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by py
 */
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Utilisateur findByUsername(String username);

    Utilisateur findOneByEmail(String email);
}
