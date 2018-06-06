package com.ex.trace.controller;

import com.ex.trace.domaine.Commentaire;
import com.ex.trace.domaine.Trace;
import com.ex.trace.service.CommentaireService;
import com.ex.trace.service.TraceService;
import com.ex.trace.service.dto.CommentaireDTO;
import com.ex.trace.service.dto.TraceDTO;
import com.ex.trace.service.mapper.CommentaireMapper;
import com.ex.trace.service.mapper.TraceMapper;
import com.ex.trace.service.mapper.TraceMapperComplet;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller
 */
@RestController
@RequestMapping("/api/admin")
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
    public ResponseEntity AfficherTouteTrace ( @RequestParam(value = "critere", required = false ) String criteria ) {
        log.debug("requete REST pour obtenir une liste de Trace");
        List<TraceDTO>  tracesDTO = null;
/*        try {*/
            List<Trace> traces =  traceService.afficherTout(criteria);
            tracesDTO = traceMapperComplet.toDto(traces);
            return ResponseEntity.ok(tracesDTO);
/*        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("coucou");
        }*/
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
        return ResponseEntity.ok(traceDTO);
    }

    /**
     * GET  /commentaire : obtenir tout commentaire.
     *
     * @return ResponseEntity avec status 200 (OK) et la liste des commentaire dans le body
     */
    @GetMapping("/commentaire")
    public ResponseEntity< List<CommentaireDTO> >  afficherToutCommentaire ( @RequestParam(value = "critere", required = false ) String criteria ) {
        log.debug("requete REST pour obtenir une liste de Commentaire avec criteria: {}", criteria);
        List<Commentaire> commentaires = commentaireService.findAll( criteria);
        List<CommentaireDTO> commentairesDTO = commentaireMapper.toDto( commentaires);
        return ResponseEntity.ok(commentairesDTO);
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
        Commentaire commentaire = commentaireService.findOne(id);
        CommentaireDTO commentaireDTO = commentaireMapper.toDto(commentaire);
        return ResponseEntity.ok(commentaireDTO);
    }
}
