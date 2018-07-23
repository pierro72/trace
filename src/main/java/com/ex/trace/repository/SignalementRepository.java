package com.ex.trace.repository;

import com.ex.trace.domaine.Message;
import com.ex.trace.domaine.Signalement;
import com.ex.trace.domaine.security.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the Tuile entity.
 */
@Repository
public interface SignalementRepository extends JpaRepository<Signalement, Long>{


    Signalement findByMessageAndUtilisateur(Message m, Utilisateur u);

    void deleteByMessageAndUtilisateur(Message m, Utilisateur u);

}
