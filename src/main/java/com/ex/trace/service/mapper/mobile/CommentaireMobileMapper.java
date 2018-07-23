package com.ex.trace.service.mapper.mobile;

import com.ex.trace.domaine.Commentaire;
import com.ex.trace.domaine.security.Utilisateur;
import com.ex.trace.repository.TraceRepository;
import com.ex.trace.security.repository.UtilisateurRepository;
import com.ex.trace.service.TraceService;
import com.ex.trace.service.dto.mobile.CommentaireDTO;
import com.ex.trace.service.dto.mobile.PostCommentaireDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { TraceService.class })
public abstract class CommentaireMobileMapper {


    @Autowired
    public UtilisateurRepository utilisateurRepository;
    @Autowired
    public TraceRepository traceRepository;

    public abstract List<CommentaireDTO> toDto (List<Commentaire> commentaires);


    public Commentaire toEntity (PostCommentaireDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new Commentaire (
            dto.getContenu(),
            utilisateurRepository.findByUsername( authentication.getName()),
            traceRepository.findOne( dto.getTraceId())
        );
    }

    public CommentaireDTO toDto (Commentaire entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utilisateur utilisateur = utilisateurRepository.findByUsername( authentication.getName());

        return new CommentaireDTO (
            entity.getId(),
            entity.getContenu(),
            entity.getDate(),
            entity.getTotalLike(),
            entity.getTotalSignalement(),
            MapperUtil.estLike( entity, utilisateur),
            MapperUtil.estSignale( entity, utilisateur),
            MapperUtil.estProprietaire( entity, utilisateur),
            entity.getTrace().getId()
        );
    }

}
