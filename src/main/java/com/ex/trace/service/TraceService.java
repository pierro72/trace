package com.ex.trace.service;

import com.ex.trace.domaine.Trace;
import com.ex.trace.domaine.Visite;
import com.ex.trace.domaine.security.Utilisateur;
import com.ex.trace.exception.ResourceNotFoundException;
import com.ex.trace.exception.TraceNotProxiException;
import com.ex.trace.repository.TraceRepository;
import com.ex.trace.repository.VisiteRepository;
import com.ex.trace.security.repository.UtilisateurRepository;
import com.ex.trace.specification.TraceSpecification;
import com.ex.trace.util.SearchCriteria;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation de service pour  le management de  Trace.
 */
@Service
public class TraceService extends MessageService{

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Autowired
    VisiteRepository visiteRepository;

    private final Logger log = LoggerFactory.getLogger(TraceService.class);
    private final TraceRepository traceRepository;

    public TraceService( TraceRepository traceRepository) {
        this.traceRepository            = traceRepository;
    }


    /**
     * Save a trace.
     *
     * @param trace the entity pour sauvegarder
     * @return the persisted entity
     */
    public Trace ajouter ( Trace trace) {
        log.debug("Request pour sauvegarder Trace : {}", trace);
        Utilisateur utilisateur   = utilisateurService.obtenirUtilisateurCourant();
        Trace result                = traceRepository.save( trace);
        try {
            visiteRepository.save( new Visite( result, utilisateur));
        } catch (Exception ex) {
            System.out.println(ex.getCause().getMessage());
        } finally {
            return result;
        }
    }

    /**
     * Get all the trace.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Trace> obtenirToutAvecRestrictionPosition ( double positionX, double positionY) {
        double longitudeMax  = positionX + distanceVisible;
        double longitudeMin  = positionX - distanceVisible;
        double latitudeMax   = positionY + distanceVisible;
        double latitudeMin   = positionY - distanceVisible;
        return traceRepository.obtenirToutAvecRestrictionPosition( longitudeMax, longitudeMin, latitudeMax, latitudeMin );
    }

    public Trace obtenirAvecRestrictionPosition ( Long id, double positionX, double positionY ) throws TraceNotProxiException {
        log.debug("Request pour obtenir la trace: {}", id);
        Trace trace = this.obtenirSansRestriction(id);
        if ( !estLisible( positionX, positionY, trace) ){
            throw new TraceNotProxiException(id, TraceNotProxiException.ERR1);
        }
/*        this.ajouterVisite( trace);*/
        return trace;
    }

    public void ajouterVisite(Trace trace){
        try {
            visiteRepository.save( new Visite( trace, utilisateurService.obtenirUtilisateurCourant() ));
        } catch (Exception ex) {
            System.out.println(ex.getCause().getMessage());
        }
    }

    /**
     * Get all the trace.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Trace> obtenirToutSansRestriction ( String search, Pageable pageable) {

        Pattern pattern = Pattern.compile("([a-zA-Z_0-9\\.]+)(:|<|>)([a-zA-Z_0-9\\.\\-]+|[+-]?([0-9]*[.])?[0-9]+);", Pattern.UNICODE_CHARACTER_CLASS);
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
    public Trace obtenirSansRestriction ( Long id) throws ResourceNotFoundException {
        log.debug("Request to get Trace : {}", id);
        Trace trace = traceRepository.findOne(id);
        if ( trace == null) {
            throw new ResourceNotFoundException( Trace.class.getSimpleName(), id);
        }
        return trace;
    }



    public Trace validerTrace ( Long id, boolean verifier){
        Trace trace = obtenirSansRestriction( id);
        trace.setEstVerifier( verifier);
        traceRepository.save(trace);
        return trace;
    }

    /**
     * Delete the commentaire by id.
     *
     * @param id the id of the entity
     */
    public void supprimer ( Long id) {
        log.debug("Request to supprimer Commentaire : {}", id);
        Trace trace = traceRepository.findOne(id);
        if (trace == null){
            throw new ResourceNotFoundException( Trace.class.getSimpleName(), id);
        }
        traceRepository.delete(trace);
    }

    private Specification<Trace> specificationBuild ( List<SearchCriteria> params ) {
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
