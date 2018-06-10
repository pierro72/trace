package com.ex.trace.controller;

import com.ex.trace.domaine.Commentaire;
import com.ex.trace.service.CommentaireService;
import com.ex.trace.service.dto.CommentaireDTO;
import com.ex.trace.service.mapper.CommentaireMapper;
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

    private final CommentaireMapper commentaireMapper;

    public CommentaireController( CommentaireService commentaireService , CommentaireMapper commentaireMapper) {
        this.commentaireService = commentaireService;
        this.commentaireMapper  = commentaireMapper;
    }

    @GetMapping("/{traceId}/commentaire")
    public ResponseEntity< List<CommentaireDTO>> lireCommentaireParTrace ( @PathVariable Long traceId, @RequestParam double positionX, @RequestParam double positionY ) {
        log.debug("requete REST pour obtenir une liste de Commentaire");
        List<Commentaire>  commentaires = commentaireService.LireCommentaireParTrace( traceId, positionX, positionY );
        List<CommentaireDTO> commentairesDTO = commentaireMapper.toDto(commentaires);
        return new ResponseEntity<>(commentairesDTO, HttpStatus.OK);
    }


    /**
     * POST  /commentaire : Creer un nouveau commentaire
     *
     * @param commentaireDTO : commentaire a créer
     * @return  ResponseEntity avec status 201 (Creér) et avec le commentaire dans le body, ou status 400 (Bad Request) si le commentaire a déja un ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commentaire")
    public ResponseEntity<CommentaireDTO> ajouterCommentaire ( @Valid @RequestBody CommentaireDTO commentaireDTO, @RequestParam double positionX, @RequestParam double positionY) throws URISyntaxException {
        log.debug("requete REST pour sauvegarder Commentaire : {}", commentaireDTO);
        Commentaire commentaire = commentaireService.ajouter( commentaireMapper.toEntity(commentaireDTO), positionX, positionY);
        CommentaireDTO result = commentaireMapper.toDto( commentaire);
        return ResponseEntity.created(
            new URI("/api/commentaire/" + result.getId()))
            .headers(HeaderUtil.ajouterAlert( ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

}
