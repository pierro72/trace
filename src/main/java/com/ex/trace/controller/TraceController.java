package com.py.trace.controller;


import com.py.trace.service.TraceService;
import com.py.trace.service.dto.TraceDTO;
import com.py.trace.util.HeaderUtil;
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
 * REST controller for managing Trace.
 */
@RestController
@RequestMapping("/api")
public class TraceController {

    private final Logger log = LoggerFactory.getLogger(TraceController.class);

    private static final String ENTITY_NAME = "trace";

    private final TraceService traceService;

    public TraceController(TraceService traceService) {
        this.traceService = traceService;
    }


    /**
     * POST  /trace : Creer un nouveau trace
     *
     * @param traceDTO : trace a créer
     * @return  ResponseEntity avec status 201 (Creér) et avec le trace dans le body, ou status 400 (Bad Request) si le trace a déja un ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trace")
    public ResponseEntity<TraceDTO> createTrace(@Valid @RequestBody TraceDTO traceDTO) throws URISyntaxException {
        log.debug("requete REST pour sauvegarder Trace : {}", traceDTO);
        TraceDTO result = traceService.save(traceDTO);

        return ResponseEntity.created(
                new URI("/api/trace/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /trace : Updates an existing traceGroupe.
     *
     * @param traceDTO the traceGroupeDTO à metre à jours
     * @return ResponseEntity avec status 200 (OK) et avech le traceGroupeDTO mise à jours
     * or with status 400 (Bad Request) if the traceGroupeDTO n'est pas valide,
     * or with status 500 (Internal Server Error) if the traceGroupeDTO ne peux etre mise à jours
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trace")
    public ResponseEntity<TraceDTO>       updateTraceGroupe(@Valid @RequestBody TraceDTO traceDTO) throws URISyntaxException {
        log.debug("requete REST to update TraceGroupe : {}", traceDTO);
        if (traceDTO.getId() == null) {
            return createTrace(traceDTO);
        }
        TraceDTO result = traceService.save(traceDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, traceDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET  /trace : get all the trace.
     *
     * @return ResponseEntity avec status 200 (OK) et la liste des trace dans le body
     */
    @GetMapping("/trace")
    public List<TraceDTO> getAllTrace(@RequestParam(value = "search", required = false ) String criteria ) {
        log.debug("requete REST pour obtenir une liste de Trace avec criteria: {}", criteria);
        return traceService.findAll(criteria);
    }

    /**
     * GET  /trace/:id : get the "id" trace.
     *
     * @param id l'id du trace à retourner
     * @return  ResponseEntity avec status 200 (OK) et avec le trace dans le body, ou status 404 (Not Found)
     */

    @GetMapping("/trace/{id}")
    public ResponseEntity<TraceDTO> getTrace(@PathVariable Long id) {
        log.debug("requete REST to get Trace : {}", id);
        TraceDTO traceDTO = traceService.findOne(id);
        return new ResponseEntity<>(traceDTO, HttpStatus.FOUND);
    }


    /**
     * DELETE  /trace/:id : supprime le trace avec cette "id".
     *
     * @param id l'id du  trace à supprimer
     * @return ResponseEntity avec status 200 (OK)
     */
    @DeleteMapping("/trace/{id}")
    public ResponseEntity<Void> deleteTrace(@PathVariable Long id) {
        log.debug("requete REST to delete Trace : {}", id);
        traceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
