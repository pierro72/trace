package com.ex.demo.carte.controller;

import com.ex.demo.util.HeaderUtil;
import com.ex.demo.carte.service.dto.TuileGroupeDTO;
import com.ex.demo.carte.service.TuileGroupeService;
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
 * REST controller for managing TuileGroupe.
 */
@RestController
@RequestMapping("/api")
public class TuileGroupeController {

    private final Logger log = LoggerFactory.getLogger(TuileGroupeController.class);

    private static final String ENTITY_NAME = "tuileGroupe";

    private final TuileGroupeService tuileGroupeService;

    public TuileGroupeController(TuileGroupeService tuileGroupeService) {
        this.tuileGroupeService = tuileGroupeService;
    }


    /**
     * POST  /carte-groupe : Creer un nouveau tuileGroupe
     *
     * @param tuileGroupeDTO : tuileGroupe a créer
     * @return  ResponseEntity avec status 201 (Creér) et avec le tuileGroupe dans le body, ou status 400 (Bad Request) si le tuileGroupe a déja un ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tuile-groupe")

    public ResponseEntity<TuileGroupeDTO>       createTuileGroupe(@Valid @RequestBody TuileGroupeDTO tuileGroupeDTO) throws URISyntaxException {
        log.debug("requete REST pour sauvegarder TuileGroupe : {}", tuileGroupeDTO);
/*        if (tuileGroupe.getId() != null) {
            throw new BadRequestAlertException("A new tuileGroupe cannot already have an ID", ENTITY_NAME, "idexists");
        }*/
        TuileGroupeDTO result = tuileGroupeService.save(tuileGroupeDTO);

        return ResponseEntity.created(
                new URI("/api/carte-groupe/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /carte-groupes : Updates an existing tuileGroupe.
     *
     * @param tuileGroupeDTO the tuileGroupeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tuileGroupeDTO,
     * or with status 400 (Bad Request) if the tuileGroupeDTO n'est pas valide,
     * or with status 500 (Internal Server Error) if the tuileGroupeDTO ne peux etre mise à jours
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tuile-groupe")
    public ResponseEntity<TuileGroupeDTO>       updateTuileGroupe(@Valid @RequestBody TuileGroupeDTO tuileGroupeDTO) throws URISyntaxException {
        log.debug("requete REST to update TuileGroupe : {}", tuileGroupeDTO);
        if (tuileGroupeDTO.getId() == null) {
            return createTuileGroupe(tuileGroupeDTO);
        }
        TuileGroupeDTO result = tuileGroupeService.save(tuileGroupeDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tuileGroupeDTO.getId().toString()))
                .body(result);
    }


    /**
     * GET  /carte-groupe: get all the tuileGroupe.
     *
     * @return ResponseEntity avec status 200 (OK) et la liste des tuileGroupe dans le body
     */
    @GetMapping("/tuile-groupe")
    public ResponseEntity<List<TuileGroupeDTO>> getAllTuileGroupe(@RequestParam(value = "search", required = false) String criteria ) {
        log.debug("requete REST pour obtenir une liste de TuileGroupe avec criteres: {}", criteria);
        List<TuileGroupeDTO> entityList = tuileGroupeService.findAll(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /carte-groupe/:id : get the "id" tuileGroupe.
     *
     * @param id l'id du tuileGroupe à retourner
     * @return  ResponseEntity avec status 200 (OK) et avec le tuileGroupe dans le body, ou status 404 (Not Found)
     */

    @GetMapping("/tuile-groupe/{id}")
    public ResponseEntity<TuileGroupeDTO>       getTuileGroupe(@PathVariable Long id) {
        log.debug("requete REST to get TuileGroupe : {}", id);
        TuileGroupeDTO tuileGroupeDTO = tuileGroupeService.findOne(id);
        return new ResponseEntity<>(tuileGroupeDTO, HttpStatus.FOUND);
    }


    /**
     * DELETE  /carte-groupes/:id : supprime le tuileGroupe avec cette "id".
     *
     * @param id l'id du  tuileGroupe à supprimer
     * @return ResponseEntity avec status 200 (OK)
     */
    @DeleteMapping("/tuile-groupe/{id}")
    public ResponseEntity<Void>                 deleteTuileGroupe(@PathVariable Long id) {
        log.debug("requete REST to delete TuileGroupe : {}", id);
        tuileGroupeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
