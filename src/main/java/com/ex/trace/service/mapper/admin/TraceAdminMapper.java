package com.ex.trace.service.mapper.admin;

import com.ex.trace.domaine.Trace;
import com.ex.trace.service.dto.admin.TraceDTO;
import com.ex.trace.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity Trace and its DTO TraceDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,  uses = { CommentaireAdminMapper.class} )
public interface TraceAdminMapper extends EntityMapper<TraceDTO, Trace> {

    TraceDTO    toDto(Trace trace);

}
