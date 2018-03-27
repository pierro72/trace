package com.ex.demo.test.service;

import com.ex.demo.test.domaine.Animal;
import com.ex.demo.test.repository.AnimalRepository;
import com.ex.demo.test.service.dto.AnimalDTO;
import com.ex.demo.test.service.mapper.complet.AnimalMapper;
import com.ex.demo.test.specification.AnimalSpecification;
import com.ex.demo.util.SearchCriteria;
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
 * Implementation de service pour  le management de  Animal.
 */
@Service
@Transactional
public class AnimalService {

    private final Logger log = LoggerFactory.getLogger(AnimalService.class);

    private final AnimalRepository animalRepository;

    private final AnimalMapper animalMapper;

    public AnimalService(AnimalRepository animalRepository, AnimalMapper animalMapper) {
        this.animalRepository = animalRepository;
        this.animalMapper = animalMapper;
    }

    /**
     * Save a test.
     *
     * @param animalDTO the entity pour sauvegarder
     * @return the persisted entity
     */
    public AnimalDTO save(AnimalDTO animalDTO) {
        log.debug("Request pour sauvegarder Animal : {}", animalDTO);
        Animal animal = animalMapper.toEntity(animalDTO);
        animal = animalRepository.save(animal);
        return animalMapper.toDto(animal);
    }

    /**
     * Get all the animal.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AnimalDTO> findAll(String search) {
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        while (matcher.find()) {
            params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
        }
        Specification<Animal> spec = specificationBuild(params);
        List<Animal> animal = animalRepository.findAll(spec);
        return animalMapper.toDto(animal);
    }

    /**
     * Get one animal by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public AnimalDTO findOne(Long id) {
        log.debug("Request to get Animal : {}", id);
        Animal animal = animalRepository.findOne(id);

        return animalMapper.toDto(animal);
    }

    /**
     * Delete the animal by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Animal2 : {}", id);
        Animal animal = animalRepository.findOne(id);
        animalRepository.delete(animal);
    }

    private Specification<Animal> specificationBuild(List<SearchCriteria> params ) {
        if (params.size() == 0) { return null; }

        List<Specification<Animal>> specs = new ArrayList<Specification<Animal>>();
        for (SearchCriteria param : params) {
            specs.add(new AnimalSpecification(param));
        }

        Specification<Animal> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specifications.where(result).and(specs.get(i));
        }
        return result;
    }

}
