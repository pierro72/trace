package com.ex.trace.service;

import com.ex.trace.domaine.Commentaire;
import com.ex.trace.repository.CommentaireRepository;
import com.ex.trace.service.dto.CommentaireDTO;
import com.ex.trace.service.mapper.CommentaireMapper;
import com.ex.trace.specification.CommentaireSpecification;
import com.ex.trace.util.SearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Implementation de service pour  le management de  Commentaire.
 */
@Service
@Transactional
public class CommentaireService {

    private final Logger log = LoggerFactory.getLogger(CommentaireService.class);

    private final CommentaireRepository commentaireRepository;

    private final CommentaireMapper commentaireMapper;


    public CommentaireService(CommentaireRepository commentaireRepository, CommentaireMapper commentaireMapper) {
        this.commentaireRepository    = commentaireRepository;
        this.commentaireMapper        = commentaireMapper;
    }

    /**
     * Save a commentaire.
     *
     * @param commentaireDTO the entity pour sauvegarder
     * @return the persisted entity
     */
    public CommentaireDTO save(CommentaireDTO commentaireDTO) {
        log.debug("Request pour sauvegarder Commentaire : {}", commentaireDTO);
        Commentaire commentaire = commentaireMapper.toEntity(commentaireDTO);
        commentaire.setDate(Calendar.getInstance().getTime());
        commentaire = commentaireRepository.save(commentaire);
        return commentaireMapper.toDto(commentaire);
    }

    /**
     * Get all the commentaire.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CommentaireDTO> findAll(String search) {
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        while (matcher.find()) {
            params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
        }
        Specification<Commentaire> spec = specificationBuild(params);
        List<Commentaire> commentaire = commentaireRepository.findAll(spec);
        return commentaireMapper.toDto(commentaire);
    }

    /**
     * Get one commentaire by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public CommentaireDTO findOne(Long id) {
        log.debug("Request to get Commentaire : {}", id);
        Commentaire commentaire = commentaireRepository.findOne(id);

        return commentaireMapper.toDto(commentaire);
    }

    /**
     * Delete the commentaire by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Commentaire2 : {}", id);
        Commentaire commentaire = commentaireRepository.findOne(id);
        commentaireRepository.delete(commentaire);
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
