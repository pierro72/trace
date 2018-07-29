package com.ex.trace.service;

import com.ex.trace.domaine.Trace;
import com.ex.trace.domaine.Visite;
import com.ex.trace.domaine.security.Utilisateur;
import com.ex.trace.exception.ResourceNotFoundException;
import com.ex.trace.exception.MessageNotProxiException;
import com.ex.trace.repository.TraceRepository;
import com.ex.trace.repository.VisiteRepository;
import com.ex.trace.specification.TraceSpecification;
import com.ex.trace.util.SearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
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

    private final Logger log = LoggerFactory.getLogger(TraceService.class);
    private final TraceRepository traceRepository;
    private final VisiteRepository visiteRepository;

    public TraceService( TraceRepository traceRepository, VisiteRepository visiteRepository) {
        this.traceRepository  = traceRepository;
        this.visiteRepository = visiteRepository;
    }


    /**
     * Save a trace.
     *
     * @param trace La trace à sauvegarder
     * @return La trace  persisté
     */
    public Trace ajouter ( Trace trace) {
        log.debug("Request pour sauvegarder Trace : {}", trace);
        return traceRepository.save( trace);
    }

    /**
     * Obtenir toute les traces en fonction des cordonnées GPS
     *
     * @return la liste des traces
     */
    @Transactional(readOnly = true)
    public List<Trace> obtenirToutAvecGPS ( double posX, double posY) {
        return traceRepository.obtenirToutAvecGPS( posX + VISIBLE, posX - VISIBLE, posY + VISIBLE, posY - VISIBLE );
    }

    /**
     * Obtenir une trace avec son id.
     *
     * @param id L'id de la trace
     * @return la  trace
     */
    public Trace obtenirAvecGPS ( Long id, double posX, double posY ) throws MessageNotProxiException {
        log.debug("Request pour obtenir la trace: {}", id);
        Trace trace = this.obtenirSansRestriction(id);
        if ( !estLisible( posX, posY, trace) ){
            throw new MessageNotProxiException( Trace.class.getName(), id, MessageNotProxiException.ERR_TRACE_1);
        }
        return trace;
    }


    /**
     * Obtenir toute les traces
     *
     * @return la liste des traces
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
     * Obtenir une trace avec son id.
     *
     * @param id L'id de la trace
     * @return la  trace
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

    public void ajouterVisite(Trace trace){
        try {
            visiteRepository.save( new Visite( trace, utilisateurService.obtenirUtilisateurCourant() ));
        } catch (Exception ex) {
            System.out.println(ex.getCause().getMessage());
        }
    }


}
