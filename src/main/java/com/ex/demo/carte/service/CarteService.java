package com.ex.demo.carte.service;

import com.ex.demo.carte.domaine.Carte;
import com.ex.demo.carte.service.dto.CarteDTO;
import com.ex.demo.carte.service.mapper.complet.CarteMapperComplet;
import com.ex.demo.carte.repository.CarteRepository;
import com.ex.demo.carte.service.mapper.simple.CarteMapper;
import com.ex.demo.util.SearchCriteria;
import com.ex.demo.carte.specification.CarteSpecification;
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
 * Implementation de service pour  le management de  Carte.
 */
@Service
@Transactional
public class CarteService {

    private final Logger log = LoggerFactory.getLogger(CarteService.class);

    private final CarteRepository carteRepository;

    private final com.ex.demo.carte.service.mapper.simple.CarteMapper carteMapper;

    private final CarteMapperComplet carteMapperComplet;

    public CarteService(CarteRepository carteRepository, CarteMapper carteMapper, CarteMapperComplet carteMapperComplet) {
        this.carteRepository = carteRepository;
        this.carteMapper = carteMapper;
        this.carteMapperComplet = carteMapperComplet;
    }

    /**
     * Save a carte.
     *
     * @param carteDTO the entity pour sauvegarder
     * @return the persisted entity
     */
    public CarteDTO save(CarteDTO carteDTO) {
        log.debug("Request pour sauvegarder Carte : {}", carteDTO);
        Carte carte = carteMapper.toEntity(carteDTO);
        carte = carteRepository.save(carte);
        return carteMapper.toDto(carte);
    }

    /**
     * Get all the carte.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CarteDTO> findAll(String search) {
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        while (matcher.find()) {
            params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
        }
        Specification<Carte> spec = specificationBuild(params);
        List<Carte> carte = carteRepository.findAll(spec);
        return carteMapper.toDto(carte);
    }

    /**
     * Get one carte by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public CarteDTO findOne(Long id) {
        log.debug("Request to get Carte : {}", id);
        Carte carte = carteRepository.findOne(id);

        return carteMapperComplet.toDto(carte);
    }

    /**
     * Delete the carte by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Carte2 : {}", id);
        Carte carte = carteRepository.findOne(id);
        carteRepository.delete(carte);
    }

    private Specification<Carte> specificationBuild(List<SearchCriteria> params ) {
        if (params.size() == 0) { return null; }

        List<Specification<Carte>> specs = new ArrayList<Specification<Carte>>();
        for (SearchCriteria param : params) {
            specs.add(new CarteSpecification(param));
        }

        Specification<Carte> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specifications.where(result).and(specs.get(i));
        }
        return result;
    }

}
