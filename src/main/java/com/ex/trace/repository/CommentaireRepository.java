package com.ex.trace.repository;


import com.ex.trace.domaine.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the Tuile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Long>, JpaSpecificationExecutor<Commentaire> {

    Commentaire findById(Long id);
}
