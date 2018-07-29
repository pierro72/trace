package com.ex.trace.controller.mobile;

import com.ex.trace.domaine.Commentaire;
import com.ex.trace.domaine.Trace;
import com.ex.trace.service.CommentaireService;
import com.ex.trace.service.dto.mobile.CommentaireDTO;
import com.ex.trace.service.dto.mobile.TraceDTO;
import com.ex.trace.service.dto.mobile.post.PostCommentaireDTO;
import com.ex.trace.service.mapper.mobile.CommentaireMobileMapper;
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
 * REST controller for managing Commentaire.
 */
@RestController
@RequestMapping("/api/mobile")
@Api( description = "Operations reservée aux utilisateurs pour voir/ajouter des commentaires")
public class CommentaireController extends MessageController {

    private final Logger log = LoggerFactory.getLogger(CommentaireController.class);
    private static final String ENTITY_NAME = "commentaire";
    @Autowired private CommentaireService commentaireService;
    @Autowired private CommentaireMobileMapper mapper;


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/trace/{traceId}/commentaire")
    public ResponseEntity< List<CommentaireDTO>> obtenirToutParTrace (@PathVariable Long traceId, @RequestParam double positionX, @RequestParam double positionY ) {
        log.debug("requete REST pour obtenir une liste de Commentaire");
        List<Commentaire>  commentaires = commentaireService.ObtenirToutParTraceEtGPS( traceId, positionX, positionY );
        List<CommentaireDTO> commentairesDTO = mapper.toDto(commentaires);
        return new ResponseEntity<>(commentairesDTO, HttpStatus.OK);
    }

/*
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/commentaire/{id}")
    public ResponseEntity<CommentaireDTO> obtenir(@PathVariable Long id, @RequestParam double positionX, @RequestParam double positionY ) {
        log.debug("requete REST to get Trace : {}", id);
        Commentaire commentaire         = commentaireService.obtenirAvecGPS( id, positionX, positionY);
        CommentaireDTO commentaireDTO   =  mapper.toDto(commentaire)
        return new ResponseEntity<>(commentaireDTO, HttpStatus.OK);
    }
*/

    /**
     * POST  /commentaire : Creer un nouveau commentaire
     *
     * @param postDTO : commentaire a créer
     * @return  ResponseEntity avec status 201 (Creér) et avec le commentaire dans le body, ou status 400 (Bad Request) si le commentaire a déja un ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/trace/{traceId}/commentaire")
    public ResponseEntity<CommentaireDTO> ajouter (@Valid @RequestBody PostCommentaireDTO postDTO, @RequestParam double positionX, @RequestParam double positionY) throws URISyntaxException {
        log.debug("requete REST pour sauvegarder Commentaire : {}", postDTO);
        Commentaire commentaire = commentaireService.ajouterAvecGPS( mapper.toEntity(postDTO), positionX, positionY);
        CommentaireDTO result = mapper.toDto( commentaire);
        return ResponseEntity.created(
            new URI("/api/commentaire/" + result.getId()))
            .headers(HeaderUtil.ajouterAlert( ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @Override
    @PostMapping( "/commentaire/{id}/recommandation" )
    public ResponseEntity<Void> ajouterRecommandation(Long id, double positionX, double positionY) {
        Commentaire commentaire = commentaireService.ObtenirAvecGPS(id, positionX, positionY);
        commentaireService.ajouterRecommandation( commentaire);
        return ResponseEntity.ok().headers(HeaderUtil.ajouterAlert(ENTITY_NAME, id.toString())).build();
    }

    @Override
    @DeleteMapping( "/commentaire/{id}/recommandation" )
    public ResponseEntity<Void> supprimerRecommandation(Long id, double positionX, double positionY) {
        Commentaire commentaire = commentaireService.ObtenirAvecGPS(id, positionX, positionY);
        commentaireService.supprimerRecommandation( commentaire);
        return ResponseEntity.ok().headers(HeaderUtil.supprimerAlert(ENTITY_NAME, id.toString())).build();
    }

    @Override
    @PostMapping( "/commentaire/{id}/signalement" )
    public ResponseEntity<Void> ajouterSignalement(Long id, double positionX, double positionY) {
        Commentaire commentaire = commentaireService.ObtenirAvecGPS(id, positionX, positionY);
        commentaireService.ajouterSignalement( commentaire);
        return ResponseEntity.ok().headers(HeaderUtil.ajouterAlert(ENTITY_NAME, id.toString())).build();
    }

    @Override
    @DeleteMapping( "/commentaire/{id}/signalement" )
    public ResponseEntity<Void> supprimerSignalement(Long id, double positionX, double positionY) {
        Commentaire commentaire = commentaireService.ObtenirAvecGPS(id, positionX, positionY);
        commentaireService.supprimerSignalement( commentaire);
        return ResponseEntity.ok().headers(HeaderUtil.supprimerAlert(ENTITY_NAME, id.toString())).build();
    }
}
