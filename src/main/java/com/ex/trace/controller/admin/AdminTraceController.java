package com.ex.trace.controller.admin;

import com.ex.trace.controller.mobile.CommentaireController;
import com.ex.trace.domaine.Trace;
import com.ex.trace.service.TraceService;
import com.ex.trace.service.dto.admin.TraceDTO;
import com.ex.trace.service.mapper.admin.TraceAdminMapper;
import com.ex.trace.util.HeaderUtil;
import com.ex.trace.util.PaginationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@RestController
@RequestMapping("/api/admin")
@Api( description = "Operations pour consulter/supprimer toutes les traces")
public class AdminTraceController extends AdminMessageController {

    private final Logger log = LoggerFactory.getLogger(CommentaireController.class);
    private final TraceService traceService;
    private final TraceAdminMapper mapper;

    public AdminTraceController(TraceService traceService, TraceAdminMapper mapper) {
        this.traceService = traceService;
        this.mapper = mapper;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/trace")
    @ApiOperation( value = "Obtenir toutes les traces avec des filtres de recherche")
    public ResponseEntity obtenirTout(@RequestParam(value = "critere", required = false ) String criteria, Pageable pageable ) {
        log.debug("requete REST pour obtenir une liste de Trace");
        Page<Trace> traces =  traceService.obtenirToutSansRestriction(criteria, pageable);
        Page<TraceDTO>  page = traces.map(mapper::toDto);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trace");
        return new ResponseEntity<>( page.getContent(), headers,  HttpStatus.OK);
    }

    /**
     * GET  /trace/:id : obtenir la trace avec son "id".
     *
     * @param id l'id de la trace à retourner
     * @return  ResponseEntity avec status 200 (OK) et avec la trace dans le body, ou status 404 (Not Found)
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/trace/{id}")
    @ApiOperation( value = "Obtenir une trace avec à partir de son ID")
    public ResponseEntity<TraceDTO> obtenir(@PathVariable Long id) {
        log.debug("requete REST pour obtenir une Trace : {}", id);
        Trace trace = traceService.obtenirSansRestriction( id );
        TraceDTO traceDTO =  mapper.toDto(trace);
        return new ResponseEntity<>(traceDTO, HttpStatus.OK);
    }

    /**
     * DELETE  /trace/:id : supprime la trace avec cette "id".
     *
     * @param id l'id de la trace  à supprimer
     * @return ResponseEntity avec status 200 (OK)
     */
    @Override
    @DeleteMapping("/trace/{id}")
    @ApiOperation( value = "Supprimer une trace à partir de son ID")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        log.debug("requete REST pour supprimer trace : {}", id);
        traceService.supprimer(id);
        return ResponseEntity.ok().headers(HeaderUtil.supprimerAlert(ENTITY_NAME, id.toString())).build();
    }

    @Override
    @PatchMapping("/trace/{id}")
    @ApiOperation( value = "Valider une trace à partir de son ID")
    public ResponseEntity<Void> valider( @PathVariable Long id, @RequestParam boolean estVerifier) {
        log.debug("requete REST pour valider une Trace : {}", id);
        traceService.valider(id, estVerifier );
        return ResponseEntity.ok().headers(HeaderUtil.editerAlert( ENTITY_NAME, id.toString())).build();
    }
}
