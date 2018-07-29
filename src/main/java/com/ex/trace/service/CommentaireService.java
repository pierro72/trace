package com.ex.trace.service;

import com.ex.trace.domaine.Commentaire;

import com.ex.trace.domaine.Trace;
import com.ex.trace.exception.ResourceNotFoundException;
import com.ex.trace.exception.MessageNotProxiException;
import com.ex.trace.repository.CommentaireRepository;
import com.ex.trace.specification.CommentaireSpecification;
import com.ex.trace.util.SearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Implementation de service pour  le management de  Commentaire.
 */
@Service
@Transactional
public class CommentaireService extends MessageService {

    private final TraceService traceService;
    private final CommentaireRepository commentaireRepository;
    private final Logger log = LoggerFactory.getLogger(CommentaireService.class);

    public CommentaireService( CommentaireRepository commentaireRepository, TraceService traceService ) {
        this.commentaireRepository      = commentaireRepository;
        this.traceService               = traceService;
    }



    @Transactional(readOnly = true)
    public List<Commentaire> ObtenirToutParTraceEtGPS(long idTrace, double positionX, double positionY) {
        Trace trace = traceService.obtenirAvecGPS(idTrace, positionX, positionY);
        return commentaireRepository.findAllByTrace(trace);
    }

    /**
     * Sauver un commentaire.
     *
     * @param commentaire the entity pour sauvegarder
     * @return the persisted entity
     */
    public Commentaire ajouterAvecGPS(Commentaire commentaire, double positionX, double positionY ) throws MessageNotProxiException {
        log.debug("Request pour sauvegarder Commentaire : {}", commentaire);
        if ( !estLisible ( positionX, positionY, commentaire) ) {
            throw new MessageNotProxiException( Trace.class.getName(), commentaire.getTrace().getId(), MessageNotProxiException.ERR_TRACE_1);
        }
        commentaire = commentaireRepository.save(commentaire);
        return commentaire;
    }

    public Commentaire ObtenirAvecGPS(Long id, double positionX, double positionY ) {
        log.debug("Request to get Commentaire : {}", id);
        Commentaire commentaire = this.obtenir( id);
        if ( !estLisible( positionX, positionY, commentaire) ){
            throw new MessageNotProxiException( Commentaire.class.getName() ,id, MessageNotProxiException.ERR_COMMENTAIRE_1);
        }
        return commentaire;
    }

    /**
     * Obtenir all the commentaire.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page< Commentaire > obtenirTout ( String search, Pageable pageable) {
        Pattern pattern = Pattern.compile("([a-zA-Z_0-9\\.]+)(:|<|>)([a-zA-Z_0-9\\.]+|[+-]?([0-9]*[.])?[0-9]+);", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(search + ";");
        List<SearchCriteria> searchCriterias = new ArrayList<SearchCriteria>();
        while (matcher.find()) {
            searchCriterias.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
        }
        Specification<Commentaire> spec = specificationBuild(searchCriterias);
        return commentaireRepository.findAll(spec, pageable);
    }

    /**
     * Obtenir one commentaire by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Commentaire obtenir( long id) {
        log.debug("Request to get Commentaire : {}", id);
        Commentaire commentaire = commentaireRepository.findOne(id);
        if (commentaire == null){
            throw new ResourceNotFoundException( Commentaire.class.getSimpleName(), id);
        }
        return commentaire;
    }

    private Specification<Commentaire> specificationBuild(List<SearchCriteria> params ) {
        if (params.size() == 0) { return null; }
        List<Specification<Commentaire>> specs = new ArrayList<Specification<Commentaire>>();
        for (SearchCriteria param : params) {
            specs.add(new CommentaireSpecification(param));
        }
        Specification<Commentaire> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specifications.where(result).and(specs.get(i));
        }
        return result;
    }



}
