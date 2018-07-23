package com.ex.trace.repository;


import com.ex.trace.domaine.Commentaire;
import com.ex.trace.domaine.Trace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the Tuile entity.
 */
@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Long>, JpaSpecificationExecutor<Commentaire> {

    Commentaire findById(Long id);

    List<Commentaire> findAllByTrace(Trace trace);
}
