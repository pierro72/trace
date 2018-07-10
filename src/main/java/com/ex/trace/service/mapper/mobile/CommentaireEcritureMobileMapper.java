package com.ex.trace.service.mapper.mobile;


import com.ex.trace.domaine.Commentaire;
import com.ex.trace.service.TraceService;
import com.ex.trace.service.dto.mobile.CommentairePostDTO;
import com.ex.trace.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


/**
 * Mapper for the entity Commentaire and its DTO CommentaireDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { TraceService.class })
public interface CommentaireEcritureMobileMapper extends EntityMapper<CommentairePostDTO, Commentaire> {

    @Mapping(source = "traceId", target="trace")
    Commentaire       toEntity(CommentairePostDTO commentaireDTO);

}
