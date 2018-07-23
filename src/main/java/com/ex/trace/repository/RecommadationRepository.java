package com.ex.trace.repository;

import com.ex.trace.domaine.CompositePK.UtilisateurMessageCompositePK;
import com.ex.trace.domaine.Message;
import com.ex.trace.domaine.Recommandation;
import com.ex.trace.domaine.Trace;
import com.ex.trace.domaine.security.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the Tuile entity.
 */
@Repository
public interface RecommadationRepository extends JpaRepository<Recommandation, Long>{


    Recommandation findByMessageAndUtilisateur (Message m, Utilisateur u);

    void deleteByMessageAndUtilisateur(Message m, Utilisateur u);



}


/*    @Query(INSERT)
    void ajouterRecommandation(@Param("idMessage") double idMessage, @Param("IdUtilisateur") double IdUtilisateur );

    public final static String INSERT =
            "INSERT INTO recommandation( id_message, id_utilisateur) " +
            "VALUES (:idMessage, :id_utilisateur)";*/