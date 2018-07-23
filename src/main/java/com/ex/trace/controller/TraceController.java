package com.ex.trace.controller;

import com.ex.trace.domaine.Trace;
import com.ex.trace.service.TraceService;
import com.ex.trace.service.VisiteService;
import com.ex.trace.service.dto.mobile.*;
import com.ex.trace.service.mapper.mobile.TraceMobileMapper;
import com.ex.trace.util.HeaderUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TraceController extends MessageController{

    private final Logger log = LoggerFactory.getLogger(TraceController.class);
    private static final String ENTITY_NAME = "trace";
    @Autowired private  TraceService traceService;
    @Autowired private TraceMobileMapper mapper;

    public TraceController() { }

    /**
     * POST  /trace : Creer un nouveau trace
     *
     * @param postTraceDTO : trace a créer
     * @return  ResponseEntity avec status 201 (Creér) et avec le trace dans le body, ou status 400 (Bad Request) si le trace a déja un ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/trace")
    public ResponseEntity<TraceDTO> ajouter (@Valid @RequestBody PostTraceDTO postTraceDTO) throws URISyntaxException {
        log.debug("requete REST pour sauvegarder Trace : {}", postTraceDTO);
        Trace trace = traceService.ajouter( mapper.toTrace(postTraceDTO) );
        TraceDTO result = mapper.toTraceDTO(trace);
        return ResponseEntity.created(
                new URI("/api/trace/" + result.getId()))
                .headers(HeaderUtil.ajouterAlert( ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * GET  /trace : get all the trace.
     *
     * @return ResponseEntity avec status 200 (OK) et la liste des trace dans le body
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/trace")
    public ResponseEntity< List<TraceSoftDTO> > obtenirTout (@RequestParam double positionX , @RequestParam  double positionY ) {
        log.debug("requete REST pour obtenir une liste de Trace");
        List<Trace> traces =  traceService.obtenirToutAvecRestrictionPosition( positionX, positionY);
        List<TraceSoftDTO> traceSoftDTO = mapper.toTraceSoftDTO( traces);
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
    public ResponseEntity<TraceDTO> obtenir(@PathVariable Long id, @RequestParam double positionX, @RequestParam double positionY ) {
        log.debug("requete REST to get Trace : {}", id);
        Trace trace         = traceService.obtenirAvecRestrictionPosition( id, positionX, positionY);
        TraceDTO traceDTO   =  mapper.toTraceDTO(trace);
        traceService.ajouterVisite(trace);
        return new ResponseEntity<>(traceDTO, HttpStatus.OK);
    }

    @Override
    @PostMapping( "/trace/{id}/signalement" )
    public ResponseEntity<Void> ajouterSignalement(@PathVariable Long id, @RequestParam double positionX, @RequestParam double positionY) {
        Trace trace = traceService.obtenirAvecRestrictionPosition( id, positionX, positionY);
        traceService.ajouterSignalement( trace);
        return ResponseEntity.ok().headers(HeaderUtil.ajouterAlert(ENTITY_NAME, id.toString())).build();
    }

    @Override
    @DeleteMapping( "/trace/{id}/signalement" )
    public ResponseEntity<Void> supprimerSignalement(@PathVariable Long id, @RequestParam double positionX, @RequestParam double positionY) {
        Trace trace = traceService.obtenirAvecRestrictionPosition( id, positionX, positionY);
        traceService.supprimerSignalement( trace);
        return ResponseEntity.ok().headers(HeaderUtil.supprimerAlert(ENTITY_NAME, id.toString())).build();
    }

    @Override
    @PostMapping( "/trace/{id}/recommandation" )
    public ResponseEntity<Void> ajouterRecommandation(@PathVariable Long id, @RequestParam double positionX, @RequestParam double positionY) {
        Trace trace = traceService.obtenirAvecRestrictionPosition( id, positionX, positionY);
        traceService.ajouterRecommandation( trace);
        return ResponseEntity.ok().headers(HeaderUtil.ajouterAlert(ENTITY_NAME, id.toString())).build();
    }

    @Override
    @DeleteMapping( "/trace/{id}/recommandation" )
    public ResponseEntity<Void> supprimerRecommandation(@PathVariable Long id, @RequestParam double positionX, @RequestParam double positionY) {
        Trace trace = traceService.obtenirAvecRestrictionPosition( id, positionX, positionY);
        traceService.supprimerRecommandation( trace);
        return ResponseEntity.ok().headers(HeaderUtil.supprimerAlert(ENTITY_NAME, id.toString())).build();
    }


}
