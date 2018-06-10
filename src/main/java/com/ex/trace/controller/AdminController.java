package com.ex.trace.controller;

import com.ex.trace.domaine.Commentaire;
import com.ex.trace.domaine.Trace;
import com.ex.trace.service.CommentaireService;
import com.ex.trace.service.TraceService;
import com.ex.trace.service.dto.CommentaireDTO;
import com.ex.trace.service.dto.TraceDTO;
import com.ex.trace.service.mapper.CommentaireMapper;
import com.ex.trace.service.mapper.TraceMapperComplet;
import com.ex.trace.util.HeaderUtil;
import com.ex.trace.util.PaginationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

/**
 * REST controller
 */
@RestController
@RequestMapping("/api/admin")

@Api(value = "/api/admin", description = "Operations pour consulter/supprimer toutes les traces et commentaires")
public class AdminController {

    private final Logger log = LoggerFactory.getLogger(CommentaireController.class);

    private final TraceService traceService;

    private final TraceMapperComplet traceMapperComplet;

    private final CommentaireService commentaireService;

    private final CommentaireMapper commentaireMapper;


    public AdminController( TraceService traceService, TraceMapperComplet traceMapperComplet ,CommentaireService commentaireService , CommentaireMapper commentaireMapper) {
        this.traceService = traceService;
        this.traceMapperComplet = traceMapperComplet;
        this.commentaireService = commentaireService;
        this.commentaireMapper  = commentaireMapper;
    }

    /**
     * GET  /trace : obtenir toute les traces.
     *
     * @return ResponseEntity avec status 200 (OK) et la liste des trace dans le body
     */
    @GetMapping("/trace")
    public ResponseEntity AfficherTouteTrace ( @RequestParam(value = "critere", required = false ) String criteria, Pageable pageable ) {
        log.debug("requete REST pour obtenir une liste de Trace");
        Page<Trace> traces =  traceService.afficherTout(criteria, pageable);
        Page<TraceDTO>  page = traces.map(traceMapperComplet::toDto);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trace");
        return new ResponseEntity<>(page.getContent(), headers,  HttpStatus.OK);
    }

    /**
     * GET  /trace/:id : obtenir la trace avec son "id".
     *
     * @param id l'id de la trace à retourner
     * @return  ResponseEntity avec status 200 (OK) et avec la trace dans le body, ou status 404 (Not Found)
     */
    @GetMapping("/trace/{id}")
    public ResponseEntity<TraceDTO> lireTrace ( @PathVariable Long id) {
        log.debug("requete REST pour obtenir une Trace : {}", id);
        Trace trace = null;
        trace = traceService.afficher( id );
        TraceDTO traceDTO =  traceMapperComplet.toDto(trace);
        return new ResponseEntity<>(traceDTO, HttpStatus.OK);
    }

    /**
     * GET  /commentaire : obtenir tout commentaire.
     *
     * @return ResponseEntity avec status 200 (OK) et la liste des commentaire dans le body
     */
    @GetMapping("/commentaire")
    public ResponseEntity< List<CommentaireDTO> >  afficherToutCommentaire ( @RequestParam(value = "critere", required = false ) String criteria, Pageable pageable ) {
        log.debug("requete REST pour obtenir une liste de Commentaire avec criteria: {}", criteria);
        Page <Commentaire> commentaires = commentaireService.afficherTout( criteria, pageable);
        Page <CommentaireDTO> commentairesDTO = commentaires.map(commentaireMapper::toDto);
        HttpHeaders header = PaginationUtil.generatePaginationHttpHeaders(commentairesDTO, "/api/commentaire");
        return new ResponseEntity<>(commentairesDTO.getContent(), header, HttpStatus.OK);
    }

    /**
     * GET  /commentaire/:id : obtenir commentaire avec "id".
     *
     * @param id l'id du commentaire à retourner
     * @return  ResponseEntity avec status 200 (OK) et avec le commentaire dans le body, ou status 404 (Not Found)
     */
    @GetMapping("/commentaire/{id}")
    public ResponseEntity<CommentaireDTO> lireCommentaire ( @PathVariable Long id) {
        log.debug("requete REST pour obtenir Commentaire : {}", id);
        Commentaire commentaire = commentaireService.afficher(id);
        CommentaireDTO commentaireDTO = commentaireMapper.toDto(commentaire);
        return new ResponseEntity<>(commentaireDTO, HttpStatus.OK);
    }

    /**
     * DELETE  /commentaire/:id : supprime le commentaire avec cette "id".
     *
     * @param id l'id du commentaire à supprimer
     * @return ResponseEntity avec status 200 (OK)
     */
    @DeleteMapping("/commentaire/{id}")
    public ResponseEntity<Void> supprimerCommentaire ( @PathVariable Long id) {
        log.debug("requete REST to supprimer commentaire : {}", id);
        commentaireService.supprimer(id);
        return ResponseEntity.ok().headers(HeaderUtil.supprimerAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * DELETE  /trace/:id : supprime le carte avec cette "id".
     *
     * @param id l'id de la trace  à supprimer
     * @return ResponseEntity avec status 200 (OK)
     */
    @DeleteMapping("/trace/{id}")
    public ResponseEntity<Void> supprimerTrace( @PathVariable Long id) {
        log.debug("requete REST to supprimer trace : {}", id);
        traceService.supprimer(id);
        return ResponseEntity.ok().headers(HeaderUtil.supprimerAlert(ENTITY_NAME, id.toString())).build();
    }
}
