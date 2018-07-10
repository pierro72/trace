package com.ex.trace.service.mapper.mobile;

import com.ex.trace.domaine.Commentaire;
import com.ex.trace.domaine.CommentaireLike;
import com.ex.trace.domaine.CommentaireSignalement;
import com.ex.trace.domaine.security.Utilisateur;
import com.ex.trace.service.TraceService;
import com.ex.trace.service.dto.mobile.CommentaireDTO;
import com.ex.trace.service.dto.mobile.CommentairePostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { TraceService.class })
public abstract class CommentaireMobileMapper {


    public abstract List<CommentaireDTO> toDto (List<Commentaire> trace);

    @Mapping(source = "traceId", target="trace")
    public abstract  Commentaire  toEntity( CommentairePostDTO dto);

    public CommentaireDTO toDto (Commentaire commentaire) {
        Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CommentaireDTO commentaireDTO = new CommentaireDTO();
        commentaireDTO.setId( commentaire.getId() );
        commentaireDTO.setAutheur( estProprietaire( commentaire, utilisateur) );
        commentaireDTO.setTotalLike( commentaire.getTotalLike() );
        commentaireDTO.setDate( commentaire.getDate() );
        commentaireDTO.setLike( estLike( commentaire, utilisateur));
        commentaireDTO.setSignale( estSignale( commentaire, utilisateur) );
        return commentaireDTO;
    }


    private boolean estProprietaire(Commentaire commentaire, Utilisateur utilisateur){
        return commentaire.getAutheur().getId().longValue() == utilisateur.getId().longValue();
    }


    private boolean estLike(Commentaire commentaire, Utilisateur utilisateur){
        boolean estLike = false;
        List<CommentaireLike> commentaireLikes =  commentaire.getCommentaireLike();
        for ( CommentaireLike like : commentaireLikes){
            if ( like.getUtilisateur().getId().longValue() == utilisateur.getId().longValue() ){
                estLike = true;
                break;
            }
        }
        return estLike;
    }

    private boolean estSignale(Commentaire commentaire, Utilisateur utilisateur){
        boolean estSignale = false;
        List<CommentaireSignalement> commentaireSignalements =  commentaire.getCommentaireSignalement();
        for ( CommentaireSignalement signalement : commentaireSignalements){
            if ( signalement.getUtilisateur().getId().longValue() == utilisateur.getId().longValue() ){
                estSignale = true;
                break;
            }
        }
        return estSignale;
    }




}
