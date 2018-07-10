package com.ex.trace.controller;


import com.ex.trace.domaine.Trace;
import com.ex.trace.service.TraceService;
import com.ex.trace.service.dto.mobile.TraceDTO;
import com.ex.trace.service.dto.mobile.TracePostDTO;
import com.ex.trace.service.dto.mobile.TraceSoftDTO;
import com.ex.trace.service.mapper.mobile.TraceMobileMapper;
import com.ex.trace.util.HeaderUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Api( description = "Operations reservée aux utilisateurs pour voir/ajouter des traces")
public class TraceController {

    private final Logger log = LoggerFactory.getLogger(TraceController.class);
    private static final String ENTITY_NAME = "trace";
    private final TraceService traceService;
    private final TraceMobileMapper mapper;

    public TraceController( TraceService traceService , TraceMobileMapper mapper)
    {
        this.traceService = traceService;
        this.mapper = mapper;
    }

    /**
     * POST  /trace : Creer un nouveau trace
     *
     * @param tracePostDTO : trace a créer
     * @return  ResponseEntity avec status 201 (Creér) et avec le trace dans le body, ou status 400 (Bad Request) si le trace a déja un ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/trace")
    public ResponseEntity<TraceDTO> ajouterTrace (@Valid @RequestBody TracePostDTO tracePostDTO) throws URISyntaxException {
        log.debug("requete REST pour sauvegarder Trace : {}", tracePostDTO);
        Trace trace = traceService.save( mapper.toTrace(tracePostDTO) );
        TraceDTO result = mapper.toTraceDTO(trace);
        return ResponseEntity.created(
                new URI("/api/trace/" + result.getId()))
                .headers(HeaderUtil.ajouterAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * GET  /trace : get all the trace.
     *
     * @return ResponseEntity avec status 200 (OK) et la liste des trace dans le body
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/trace")
    public ResponseEntity< List<TraceSoftDTO> > afficherTouteTrace (@RequestParam double positionX , @RequestParam  double positionY ) {
        log.debug("requete REST pour obtenir une liste de Trace");
        List<Trace> traces =  traceService.afficherToutAProximite(positionX, positionY);
        List<TraceSoftDTO> traceSoftDTO = mapper.toTraceSoftDTO(traces);
        return new ResponseEntity<>(traceSoftDTO, HttpStatus.OK);

    }

    /**
     * GET  /trace/:id : get the "id" trace.
     *
     * @param id l'id du trace à retourner
     * @return  ResponseEntity avec status 200 (OK) et avec le trace dans le body, ou status 404 (Not Found)
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/trace/{id}")
    public ResponseEntity<TraceDTO> getTrace(@PathVariable Long id, @RequestParam float positionX, @RequestParam float positionY ) {
        log.debug("requete REST to get Trace : {}", id);
        Trace trace = traceService.afficherAProximite(id, positionX, positionY);
        TraceDTO traceDTO =  mapper.toTraceDTO(trace);
        return new ResponseEntity<>(traceDTO, HttpStatus.OK);
    }



}
