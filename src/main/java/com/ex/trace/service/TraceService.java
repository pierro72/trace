package com.ex.trace.service;

import com.ex.trace.TraceType;
import com.ex.trace.domaine.Commentaire;
import com.ex.trace.domaine.Trace;
import com.ex.trace.exception.ResourceNotFoundException;
import com.ex.trace.exception.TraceNotProxiException;
import com.ex.trace.repository.TraceRepository;
import com.ex.trace.service.dto.TraceDTO;
import com.ex.trace.service.mapper.TraceMapperComplet;
import com.ex.trace.service.mapper.TraceMapper;
import com.ex.trace.specification.TraceSpecification;
import com.ex.trace.util.SearchCriteria;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation de service pour  le management de  Trace.
 */
@Service
@Transactional
public class TraceService {

    @Value( "${trace.visible}")
    private double distanceVisible;

    @Value( "${trace.lisible}")
    private double distanceLisible;

    private final Logger log = LoggerFactory.getLogger(TraceService.class);

    private final TraceRepository traceRepository;

    public TraceService(TraceRepository traceRepository) {
        this.traceRepository    = traceRepository;
    }

    /**
     * Save a trace.
     *
     * @param trace the entity pour sauvegarder
     * @return the persisted entity
     */
    public Trace save(Trace trace) {
        log.debug("Request pour sauvegarder Trace : {}", trace);
        trace.setDate(Calendar.getInstance().getTime());
        trace = traceRepository.save(trace);
        return trace;
    }

    /**
     * Get all the trace.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Trace> afficherToutAProximite(double positionX, double positionY) {
        double longitudeMax  = positionX + distanceVisible;
        double longitudeMin  = positionX - distanceVisible;
        double latitudeMax   = positionY + distanceVisible;
        double latitudeMin   = positionY - distanceVisible;
        return traceRepository.findProxiTrace( longitudeMax, longitudeMin, latitudeMax, latitudeMin );
    }

    /*FIXME: Peagable*/
    /**
     * Get all the trace.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Trace> afficherTout(String search, Pageable pageable) {

        Pattern pattern = Pattern.compile("([a-zA-Z_0-9\\.]+)(:|<|>)([a-zA-Z_0-9\\.]+|[+-]?([0-9]*[.])?[0-9]+);", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(search + ";");
        List<SearchCriteria> searchCriterias = new ArrayList<SearchCriteria>();
        while (matcher.find()) {
            searchCriterias.add( new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
        }
        Specification<Trace> spec = specificationBuild(searchCriterias);
        return traceRepository.findAll(spec, pageable);
    }

    /**
     * Get one trace by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Trace afficher(Long id) throws ResourceNotFoundException {
        log.debug("Request to get Trace : {}", id);
        Trace trace = traceRepository.findOne(id);
        if ( trace == null) {
            throw new ResourceNotFoundException( Trace.class.getSimpleName(), id);
        }
        return trace;
    }

    @Transactional(readOnly = true)
    public Trace afficherAProximite(Long id, double positionX, double positionY ) throws TraceNotProxiException {
        log.debug("Request pour obtenir la trace: {}", id);
        Trace trace = traceRepository.findOne(id);
        if ( !estLisible( positionX, positionY, trace) ){
            throw new TraceNotProxiException(id, TraceNotProxiException.ERR1);
        }
        return trace;
    }

    /**
     * Delete the commentaire by id.
     *
     * @param id the id of the entity
     */
    public void supprimer(Long id) {
        log.debug("Request to supprimer Commentaire : {}", id);
        Trace trace = traceRepository.findOne(id);
        if (trace == null){
            throw new ResourceNotFoundException( Trace.class.getSimpleName(), id);
        }
        traceRepository.delete(trace);
    }


    public boolean estLisible(double x, double y, Trace trace  ){
        double distance = obtenirDistance(x, y, trace.getPositionX(), trace.getPositionY());
        return (distance < distanceLisible);
    }

    public double obtenirDistance(double x1, double y1, double x2, double y2){
        return java.lang.Math.sqrt ( (x1-x2) * (x1-x2) + (y1-y2) * (y1-y2) );
    }

    private Specification<Trace> specificationBuild(List<SearchCriteria> params ) {
        if (params.size() == 0) { return null; }
        List<Specification<Trace>> specs = new ArrayList<Specification<Trace>>();
        for (SearchCriteria param : params) {
            specs.add(new TraceSpecification(param));
        }
        Specification<Trace> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specifications.where(result).and(specs.get(i));
        }
        return result;
    }

}
