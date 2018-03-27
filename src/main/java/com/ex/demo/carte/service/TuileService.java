package com.ex.demo.carte.service;

import com.ex.demo.carte.domaine.Tuile;
import com.ex.demo.carte.service.dto.TuileDTO;
import com.ex.demo.carte.service.mapper.complet.TuileMapper;
import com.ex.demo.carte.repository.TuileRepository;
import com.ex.demo.util.SearchCriteria;
import com.ex.demo.carte.specification.TuileSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation de service pour  le management de  Tuile.
 */
@Service
@Transactional
public class TuileService {

    private final Logger log = LoggerFactory.getLogger(TuileService.class);

    private final TuileRepository tuileRepository;

    private final TuileMapper tuileMapper;

    public TuileService(TuileRepository tuileRepository, TuileMapper tuileMapper) {
        this.tuileRepository = tuileRepository;
        this.tuileMapper = tuileMapper;
    }

    /**
     * Save a carte.
     *
     * @param tuileDTO the entity pour sauvegarder
     * @return the persisted entity
     */
    public TuileDTO save(TuileDTO tuileDTO) {
        log.debug("Request pour sauvegarder Tuile : {}", tuileDTO);
        Tuile tuile = tuileMapper.toEntity(tuileDTO);
        tuile = tuileRepository.save(tuile);
        return tuileMapper.toDto(tuile);
    }

    /**
     * Get all the tuile.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TuileDTO> findAll(String search) {
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        while (matcher.find()) {
            params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
        }
        Specification<Tuile> spec = specificationBuild(params);
        List<Tuile> tuile = tuileRepository.findAll(spec);
        return tuileMapper.toDto(tuile);
    }

    /**
     * Get one tuile by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TuileDTO findOne(Long id) {
        log.debug("Request to get Tuile : {}", id);
        Tuile tuile = tuileRepository.findOne(id);

        return tuileMapper.toDto(tuile);
    }

    /**
     * Delete the tuile by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tuile2 : {}", id);
        Tuile tuile = tuileRepository.findOne(id);
        tuileRepository.delete(tuile);
    }

    private Specification<Tuile> specificationBuild(List<SearchCriteria> params ) {
        if (params.size() == 0) { return null; }

        List<Specification<Tuile>> specs = new ArrayList<Specification<Tuile>>();
        for (SearchCriteria param : params) {
            specs.add(new TuileSpecification(param));
        }

        Specification<Tuile> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specifications.where(result).and(specs.get(i));
        }
        return result;
    }

}
