package com.ex.trace.repository;

import com.ex.trace.domaine.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by stephan on 20.03.16.
 */
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Utilisateur findByUsername(String username);
}
