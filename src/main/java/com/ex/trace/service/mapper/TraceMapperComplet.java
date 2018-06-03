package com.ex.trace.service.mapper;
import com.ex.trace.domaine.Trace;
import com.ex.trace.service.dto.TraceDTO;
import com.ex.trace.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity Trace and its DTO TraceDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,  uses = {CommentaireMapper.class} )
public interface TraceMapperComplet extends EntityMapper<TraceDTO, Trace> {

    TraceDTO    toDto(Trace trace);

    @Mapping( target = "date", ignore = true )
    @Mapping( target = "commentaires", ignore = true )
    Trace       toEntity(TraceDTO traceDTO);

}
