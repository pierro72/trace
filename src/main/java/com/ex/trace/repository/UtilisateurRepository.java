package com.ex.trace.repository;

import com.ex.trace.domaine.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    @Query(" select u from Utilisateur u " +
            " where u.nom = ?1")
    Optional<Utilisateur> findUserWithName(String name);
}
