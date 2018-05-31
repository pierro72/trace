package com.py.trace.repository;


import com.py.trace.domaine.Trace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the Tuile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TraceRepository extends JpaRepository<Trace, Long>, JpaSpecificationExecutor<Trace> {

    Trace findById(Long id);
}
