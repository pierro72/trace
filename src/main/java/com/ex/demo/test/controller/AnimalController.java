package com.ex.demo.test.controller;

import com.ex.demo.test.service.AnimalService;
import com.ex.demo.test.service.dto.AnimalDTO;
import com.ex.demo.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


/**
 * REST controller for managing Animal.
 */
@RestController
@RequestMapping("/api")
public class AnimalController {

    private final Logger log = LoggerFactory.getLogger(AnimalController.class);

    private static final String ENTITY_NAME = "animal";

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }


    /**
     * POST  /animal : Creer un nouveau animal
     *
     * @param animalDTO : animal a créer
     * @return  ResponseEntity avec status 201 (Creér) et avec le animal dans le body, ou status 400 (Bad Request) si le animal a déja un ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/animal")
    public ResponseEntity<AnimalDTO> createAnimal(@Valid @RequestBody AnimalDTO animalDTO) throws URISyntaxException {
        log.debug("requete REST pour sauvegarder Animal : {}", animalDTO);
        AnimalDTO result = animalService.save(animalDTO);

        return ResponseEntity.created(
                new URI("/api/animal/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /animal-groupes : Updates an existing animalGroupe.
     *
     * @param animalDTO the animalGroupeDTO à metre à jours
     * @return ResponseEntity avec status 200 (OK) et avech le animalGroupeDTO mise à jours
     * or with status 400 (Bad Request) if the animalGroupeDTO n'est pas valide,
     * or with status 500 (Internal Server Error) if the animalGroupeDTO ne peux etre mise à jours
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/animal")
    public ResponseEntity<AnimalDTO>       updateAnimalGroupe(@Valid @RequestBody AnimalDTO animalDTO) throws URISyntaxException {
        log.debug("requete REST to update AnimalGroupe : {}", animalDTO);
        if (animalDTO.getId() == null) {
            return createAnimal(animalDTO);
        }
        AnimalDTO result = animalService.save(animalDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, animalDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET  /animal : get all the animal.
     *
     * @return ResponseEntity avec status 200 (OK) et la liste des animal dans le body
     */
    @GetMapping("/animal")
    public List<AnimalDTO> getAllAnimal(@RequestParam(value = "search", required = false ) String criteria ) {
        log.debug("requete REST pour obtenir une liste de Animal avec criteria: {}", criteria);
        return animalService.findAll(criteria);
    }

    /**
     * GET  /animal/:id : get the "id" animal.
     *
     * @param id l'id du animal à retourner
     * @return  ResponseEntity avec status 200 (OK) et avec le animal dans le body, ou status 404 (Not Found)
     */

    @GetMapping("/animal/{id}")
    public ResponseEntity<AnimalDTO> getAnimal(@PathVariable Long id) {
        log.debug("requete REST to get Animal : {}", id);
        AnimalDTO animalDTO = animalService.findOne(id);
        return new ResponseEntity<>(animalDTO, HttpStatus.FOUND);
    }


    /**
     * DELETE  /animal/:id : supprime le animal avec cette "id".
     *
     * @param id l'id du  animal à supprimer
     * @return ResponseEntity avec status 200 (OK)
     */
    @DeleteMapping("/animal/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        log.debug("requete REST to delete Animal : {}", id);
        animalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
