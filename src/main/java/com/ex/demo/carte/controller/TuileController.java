package com.ex.demo.carte.controller;

import com.ex.demo.util.HeaderUtil;
import com.ex.demo.carte.service.dto.TuileDTO;
import com.ex.demo.carte.service.TuileService;
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
 * REST controller for managing Tuile.
 */
@RestController
@RequestMapping("/api")
public class TuileController {

    private final Logger log = LoggerFactory.getLogger(TuileController.class);

    private static final String ENTITY_NAME = "tuile";

    private final TuileService tuileService;

    public TuileController(TuileService tuileService) {
        this.tuileService = tuileService;
    }


    /**
     * POST  /tuile : Creer un nouveau tuile
     *
     * @param tuileDTO : tuile a créer
     * @return  ResponseEntity avec status 201 (Creér) et avec le tuile dans le body, ou status 400 (Bad Request) si le tuile a déja un ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tuile")
    public ResponseEntity<TuileDTO> createTuile(@Valid @RequestBody TuileDTO tuileDTO) throws URISyntaxException {
        log.debug("requete REST pour sauvegarder Tuile : {}", tuileDTO);
        TuileDTO result = tuileService.save(tuileDTO);

        return ResponseEntity.created(
                new URI("/api/tuile/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /tuile-groupes : Updates an existing tuileGroupe.
     *
     * @param tuileDTO the tuileGroupeDTO à metre à jours
     * @return ResponseEntity avec status 200 (OK) et avech le tuileGroupeDTO mise à jours
     * or with status 400 (Bad Request) if the tuileGroupeDTO n'est pas valide,
     * or with status 500 (Internal Server Error) if the tuileGroupeDTO ne peux etre mise à jours
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tuile")
    public ResponseEntity<TuileDTO>       updateTuileGroupe(@Valid @RequestBody TuileDTO tuileDTO) throws URISyntaxException {
        log.debug("requete REST to update TuileGroupe : {}", tuileDTO);
        if (tuileDTO.getId() == null) {
            return createTuile(tuileDTO);
        }
        TuileDTO result = tuileService.save(tuileDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tuileDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET  /tuile : get all the tuile.
     *
     * @return ResponseEntity avec status 200 (OK) et la liste des tuile dans le body
     */
    @GetMapping("/tuile")
    public List<TuileDTO> getAllTuile(@RequestParam(value = "search", required = false ) String criteria ) {
        log.debug("requete REST pour obtenir une liste de Tuile avec criteria: {}", criteria);
        return tuileService.findAll(criteria);
    }

    /**
     * GET  /tuile/:id : get the "id" tuile.
     *
     * @param id l'id du tuile à retourner
     * @return  ResponseEntity avec status 200 (OK) et avec le tuile dans le body, ou status 404 (Not Found)
     */

    @GetMapping("/tuile/{id}")
    public ResponseEntity<TuileDTO> getTuile(@PathVariable Long id) {
        log.debug("requete REST to get Tuile : {}", id);
        TuileDTO tuileDTO = tuileService.findOne(id);
        return new ResponseEntity<>(tuileDTO, HttpStatus.FOUND);
    }


    /**
     * DELETE  /tuile/:id : supprime le tuile avec cette "id".
     *
     * @param id l'id du  tuile à supprimer
     * @return ResponseEntity avec status 200 (OK)
     */
    @DeleteMapping("/tuile/{id}")
    public ResponseEntity<Void> deleteTuile(@PathVariable Long id) {
        log.debug("requete REST to delete Tuile : {}", id);
        tuileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
