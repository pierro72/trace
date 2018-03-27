package com.ex.demo.carte.controller;

import com.ex.demo.util.HeaderUtil;
import com.ex.demo.carte.service.dto.CarteDTO;
import com.ex.demo.carte.service.CarteService;
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
 * REST controller for managing Carte.
 */
@RestController
@RequestMapping("/api")
public class CarteController {

    private final Logger log = LoggerFactory.getLogger(CarteController.class);

    private static final String ENTITY_NAME = "carte";

    private final CarteService carteService;

    public CarteController(CarteService carteService) {
        this.carteService = carteService;
    }


    /**
     * POST  /carte : Creer un nouveau carte
     *
     * @param carteDTO : carte a créer
     * @return  ResponseEntity avec status 201 (Creér) et avec le carte dans le body, ou status 400 (Bad Request) si le carte a déja un ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/carte")
    public ResponseEntity<CarteDTO> createCarte(@Valid @RequestBody CarteDTO carteDTO) throws URISyntaxException {
        log.debug("requete REST pour sauvegarder Carte : {}", carteDTO);
        CarteDTO result = carteService.save(carteDTO);

        return ResponseEntity.created(
                new URI("/api/carte/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /carte : Updates an existing carteGroupe.
     *
     * @param carteDTO the carteGroupeDTO à metre à jours
     * @return ResponseEntity avec status 200 (OK) et avech le carteGroupeDTO mise à jours
     * or with status 400 (Bad Request) if the carteGroupeDTO n'est pas valide,
     * or with status 500 (Internal Server Error) if the carteGroupeDTO ne peux etre mise à jours
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/carte")
    public ResponseEntity<CarteDTO>       updateCarteGroupe(@Valid @RequestBody CarteDTO carteDTO) throws URISyntaxException {
        log.debug("requete REST to update CarteGroupe : {}", carteDTO);
        if (carteDTO.getId() == null) {
            return createCarte(carteDTO);
        }
        CarteDTO result = carteService.save(carteDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, carteDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET  /carte : get all the carte.
     *
     * @return ResponseEntity avec status 200 (OK) et la liste des carte dans le body
     */
    @GetMapping("/carte")
    public List<CarteDTO> getAllCarte(@RequestParam(value = "search", required = false ) String criteria ) {
        log.debug("requete REST pour obtenir une liste de Carte avec criteria: {}", criteria);
        return carteService.findAll(criteria);
    }

    /**
     * GET  /carte/:id : get the "id" carte.
     *
     * @param id l'id du carte à retourner
     * @return  ResponseEntity avec status 200 (OK) et avec le carte dans le body, ou status 404 (Not Found)
     */

    @GetMapping("/carte/{id}")
    public ResponseEntity<CarteDTO> getCarte(@PathVariable Long id) {
        log.debug("requete REST to get Carte : {}", id);
        CarteDTO carteDTO = carteService.findOne(id);
        return new ResponseEntity<>(carteDTO, HttpStatus.FOUND);
    }


    /**
     * DELETE  /carte/:id : supprime le carte avec cette "id".
     *
     * @param id l'id du  carte à supprimer
     * @return ResponseEntity avec status 200 (OK)
     */
    @DeleteMapping("/carte/{id}")
    public ResponseEntity<Void> deleteCarte(@PathVariable Long id) {
        log.debug("requete REST to delete Carte : {}", id);
        carteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
