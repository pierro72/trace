package com.ex.trace.controller.admin;

import com.ex.trace.controller.mobile.CommentaireController;
import com.ex.trace.domaine.Commentaire;
import com.ex.trace.service.CommentaireService;
import com.ex.trace.service.dto.admin.CommentaireDTO;
import com.ex.trace.service.mapper.admin.CommentaireAdminMapper;
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

import java.util.List;
import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@RestController
@RequestMapping("/api/admin")
@Api( description = "Operations pour administrer les commentaires")
public class AdminCommentaireController extends AdminMessageController {

    private final Logger log = LoggerFactory.getLogger(CommentaireController.class);
    private final CommentaireService commentaireService;
    private final CommentaireAdminMapper mapper;

    public AdminCommentaireController( CommentaireService commentaireService , CommentaireAdminMapper mapper) {
        this.commentaireService = commentaireService;
        this.mapper  = mapper;
    }

    /**
     * GET  /commentaire : obtenir tout commentaire.
     *
     * @return ResponseEntity avec status 200 (OK) et la liste des commentaire dans le body
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/commentaire")
    @ApiOperation( value = "Obtenir tous les commentaires avec des filtres de recherche")
    public ResponseEntity<List<CommentaireDTO>> obtenirTout(@RequestParam(value = "critere", required = false ) String criteria, Pageable pageable ) {
        log.debug("requete REST pour obtenir une liste de Commentaire avec criteria: {}", criteria);
        Page<Commentaire> commentaires = commentaireService.obtenirTout( criteria, pageable);
        Page <CommentaireDTO> commentairesDTO = commentaires.map(mapper::toDto);
        HttpHeaders header = PaginationUtil.generatePaginationHttpHeaders(commentairesDTO, "/api/commentaire");
        return new ResponseEntity<>(commentairesDTO.getContent(), header, HttpStatus.OK);
    }

    /**
     * GET  /commentaire/:id : obtenir commentaire avec "id".
     *
     * @param id l'id du commentaire à retourner
     * @return  ResponseEntity avec status 200 (OK) et avec le commentaire dans le body, ou status 404 (Not Found)
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/commentaire/{id}")
    @ApiOperation( value = "Obtenir une commentaire à partir de son ID")

    public ResponseEntity<CommentaireDTO> obtenir(@PathVariable Long id) {
        log.debug("requete REST pour obtenir Commentaire : {}", id);
        Commentaire commentaire = commentaireService.obtenir(id);
        CommentaireDTO dto = mapper.toDto(commentaire);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * DELETE  /commentaire/:id : supprime le commentaire avec cette "id".
     *
     * @param id l'id du commentaire à supprimer
     * @return ResponseEntity avec status 200 (OK)
     */
    @Override
    @DeleteMapping("/commentaire/{id}")
    @ApiOperation( value = "Supprimer un commentaire à partir de son ID")
    public ResponseEntity<Void> supprimer(@PathVariable  Long id) {
        log.debug("requete REST to supprimer commentaire : {}", id);
        commentaireService.supprimer(id);
        return ResponseEntity.ok().headers(HeaderUtil.supprimerAlert(ENTITY_NAME, id.toString())).build();
    }

    @Override
    @PatchMapping("/commentaire/{id}")
    @ApiOperation( value = "Valider un commentaire à partir de son ID")
    public ResponseEntity<Void> valider(@PathVariable  Long id, @RequestParam boolean estVerifier) {
        log.debug("requete REST pour valider une Trace : {}", id);
        commentaireService.valider(id, estVerifier );
        return ResponseEntity.ok().headers(HeaderUtil.editerAlert(ENTITY_NAME, id.toString())).build();
    }


}
