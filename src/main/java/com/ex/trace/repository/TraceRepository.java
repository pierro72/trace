package com.ex.trace.repository;

import com.ex.trace.domaine.Trace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the Tuile entity.
 */
 @Repository
public interface TraceRepository extends JpaRepository<Trace, Long>, JpaSpecificationExecutor<Trace> {

    public final static String TRACE_VISIBE = "SELECT t FROM Trace t " +
            "WHERE positionx <= :longitudeMax " +
            "AND positionx >= :longitudeMin " +
            "AND positiony <= :latitudeMax " +
            "AND positiony >= :latitudeMin";


    @Query(TRACE_VISIBE)
    List<Trace> obtenirToutAvecRestrictionPosition (@Param("longitudeMax") double longitudeMax,
                                                   @Param("longitudeMin") double longitudeMin,
                                                   @Param("latitudeMax") double latitudeMax,
                                                   @Param("latitudeMin") double latitudeMin);


}
