package com.ex.trace.controller;

import com.ex.trace.service.CommentaireService;
import com.ex.trace.service.dto.CommentaireDTO;
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
 * REST controller for managing Commentaire.
 */
@RestController
@RequestMapping("/api")
public class CommentaireController {

    private final Logger log = LoggerFactory.getLogger(CommentaireController.class);

    private static final String ENTITY_NAME = "commentaire";

    private final CommentaireService commentaireService;

    public CommentaireController(CommentaireService commentaireService) {
        this.commentaireService = commentaireService;
    }

    /**
     * POST  /commentaire : Creer un nouveau commentaire
     *
     * @param commentaireDTO : commentaire a créer
     * @return  ResponseEntity avec status 201 (Creér) et avec le commentaire dans le body, ou status 400 (Bad Request) si le commentaire a déja un ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commentaire")
    public ResponseEntity<CommentaireDTO> createCommentaire( @Valid @RequestBody CommentaireDTO commentaireDTO, @RequestParam double positionX, double positionY) throws URISyntaxException {
        log.debug("requete REST pour sauvegarder Commentaire : {}", commentaireDTO);
        CommentaireDTO result = null;
        try {
            result = commentaireService.save(commentaireDTO, positionX, positionY);
            return ResponseEntity.created(
                    new URI("/api/commentaire/" + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                    .body(result);
        } catch (Exception e) {
            e.getStackTrace();
            return new ResponseEntity<>(commentaireDTO, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * GET  /commentaire : get all the commentaire.
     *
     * @return ResponseEntity avec status 200 (OK) et la liste des commentaire dans le body
     */
    @GetMapping("/commentaire")
    public List<CommentaireDTO> getAllCommentaire(@RequestParam(value = "search", required = false ) String criteria ) {
        log.debug("requete REST pour obtenir une liste de Commentaire avec criteria: {}", criteria);
        return commentaireService.findAll(criteria);
    }

    /**
     * GET  /commentaire/:id : get the "id" commentaire.
     *
     * @param id l'id du commentaire à retourner
     * @return  ResponseEntity avec status 200 (OK) et avec le commentaire dans le body, ou status 404 (Not Found)
     */

    @GetMapping("/commentaire/{id}")
    public ResponseEntity<CommentaireDTO> getCommentaire(@PathVariable Long id) {
        log.debug("requete REST to get Commentaire : {}", id);
        CommentaireDTO commentaireDTO = commentaireService.findOne(id);
        return new ResponseEntity<>(commentaireDTO, HttpStatus.FOUND);
    }



}
