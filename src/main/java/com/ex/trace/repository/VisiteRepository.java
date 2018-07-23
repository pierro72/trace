package com.ex.trace.repository;

import com.ex.trace.domaine.Visite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the Tuile entity.
 */
@Repository
public interface VisiteRepository extends JpaRepository<Visite, Long>{

}
