package com.ex.demo.carte.repository;

import com.ex.demo.carte.domaine.Carte;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the Tuile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarteRepository extends JpaRepository<Carte, Long>, JpaSpecificationExecutor<Carte> {

    Carte findById(Long id);
}
