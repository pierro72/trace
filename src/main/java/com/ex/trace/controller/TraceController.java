package com.ex.trace.controller;


import com.ex.trace.service.TraceService;
import com.ex.trace.service.dto.TraceDTO;
import com.ex.trace.util.HeaderUtil;
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
     * GET  /trace : get all the trace.
     *
     * @return ResponseEntity avec status 200 (OK) et la liste des trace dans le body
     */
    @GetMapping("/trace")
    public List<TraceDTO> getAllTrace(@RequestParam double positionX, double positionY ) {
        log.debug("requete REST pour obtenir une liste de Trace");
        return traceService.findAll(positionX, positionY);
    }

    /**
     * GET  /trace/:id : get the "id" trace.
     *
     * @param id l'id du trace à retourner
     * @return  ResponseEntity avec status 200 (OK) et avec le trace dans le body, ou status 404 (Not Found)
     */

    @GetMapping("/trace/{id}")
    public ResponseEntity<TraceDTO> getTrace(@PathVariable Long id, @RequestParam float positionX, float positionY ) {
        log.debug("requete REST to get Trace : {}", id);
        TraceDTO traceDTO = null;
        try {
            traceDTO = traceService.lirePoint(id, positionX, positionY);
            return ResponseEntity.ok(traceDTO);

        } catch (Exception e) {
            return new ResponseEntity<>(traceDTO, HttpStatus.NOT_FOUND);
        }

    }



}
