package com.ex.trace.service.mapper.admin;


import com.ex.trace.domaine.Commentaire;
import com.ex.trace.service.TraceService;
import com.ex.trace.service.dto.admin.CommentaireDTO;
import com.ex.trace.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


/**
 * Mapper for the entity Commentaire and its DTO CommentaireDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { TraceService.class })
public interface CommentaireAdminMapper extends EntityMapper<CommentaireDTO, Commentaire> {

    @Mapping(source="trace.id", target="traceId")
    CommentaireDTO    toDto(Commentaire commentaire);

    @Mapping(target = "date", ignore = true)
    @Mapping(source = "traceId", target="trace")
    Commentaire       toEntity(CommentaireDTO commentaireDTO);

}
