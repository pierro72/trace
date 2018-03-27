package com.ex.demo.carte.service;

import com.ex.demo.carte.domaine.TuileGroupe;
import com.ex.demo.carte.service.dto.TuileGroupeDTO;
import com.ex.demo.carte.service.mapper.complet.TuileGroupeMapper;
import com.ex.demo.carte.repository.TuileGroupeRepository;
import com.ex.demo.util.SearchCriteria;
import com.ex.demo.carte.specification.TuileGroupeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

/**
 * Implementation de service pour  le management de  TuileGroupe.
 */
@Service
@Transactional
public class TuileGroupeService {

    private final Logger log = LoggerFactory.getLogger(TuileGroupeService.class);

    private final TuileGroupeRepository tuileGroupeRepository;

    private final TuileGroupeMapper tuileGroupeMapper;

    public TuileGroupeService(TuileGroupeRepository tuileGroupeRepository, TuileGroupeMapper tuileGroupeMapper) {
        this.tuileGroupeRepository = tuileGroupeRepository;
        this.tuileGroupeMapper = tuileGroupeMapper;
    }

    /**
     * Save a tuileGroupe.
     *
     * @param tuileGroupeDTO the entity pour sauvegarder
     * @return the persisted entity
     */
    public TuileGroupeDTO save(TuileGroupeDTO tuileGroupeDTO) {
        log.debug("Request pour sauvegarder TuileGroupe : {}", tuileGroupeDTO);
        TuileGroupe tuileGroupe = tuileGroupeMapper.toEntity(tuileGroupeDTO);
        tuileGroupe = tuileGroupeRepository.save(tuileGroupe);
        return tuileGroupeMapper.toDto(tuileGroupe);
    }

    /**
     * Get all the tuileGroupe.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TuileGroupeDTO> findAll(String search) {
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        while (matcher.find()) {
            params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
        }
        Specification<TuileGroupe> spec = specificationBuild(params);
        List<TuileGroupe> t = tuileGroupeRepository.findAll(spec);
        return tuileGroupeMapper.toDto(t);
    }

    /**
     * Get one tuileGroupe by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TuileGroupeDTO findOne(Long id) {
        TuileGroupe tuileGroupe = tuileGroupeRepository.findOne(id);
        log.debug("Request pour sauvegarder TuileGroupe : {}", tuileGroupe);
        return tuileGroupeMapper.toDto(tuileGroupe);
    }

    /**
     * Delete the tuileGroupe by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TuileGroupe : {}", id);
        tuileGroupeRepository.delete(id);
    }


    private Specification<TuileGroupe> specificationBuild(List<SearchCriteria> params ) {
        if (params.size() == 0) { return null; }

        List<Specification<TuileGroupe>> specs = new ArrayList<Specification<TuileGroupe>>();
        for (SearchCriteria param : params) {
            specs.add(new TuileGroupeSpecification(param));
        }

        Specification<TuileGroupe> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specifications.where(result).and(specs.get(i));
        }
        return result;
    }

}
