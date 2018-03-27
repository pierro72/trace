package com.ex.demo.carte.repository;

import com.ex.demo.carte.domaine.Tuile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the Tuile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TuileRepository extends JpaRepository<Tuile, Long>, JpaSpecificationExecutor<Tuile> {

    void delete(Tuile t);

    Tuile findById(Long id);
}
