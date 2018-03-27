package com.ex.demo.carte.repository;

import com.ex.demo.carte.domaine.TuileGroupe;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the TuileGroupe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TuileGroupeRepository extends JpaRepository<TuileGroupe, Long>, JpaSpecificationExecutor<TuileGroupe> {

    TuileGroupe findById(Long id);
}
