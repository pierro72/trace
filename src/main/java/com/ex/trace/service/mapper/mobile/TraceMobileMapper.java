package com.ex.trace.service.mapper.mobile;

import com.ex.trace.domaine.Trace;
import com.ex.trace.service.dto.mobile.TraceDTO;
import com.ex.trace.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity Trace and its DTO TraceDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,  uses = {CommentaireMobileMapper.class} )
public interface TraceMobileMapper extends EntityMapper<TraceDTO, Trace> {

    TraceDTO    toDto(Trace trace);


    @Mapping( target = "date",          ignore = true )
    @Mapping( target = "vue",           ignore = true )
    Trace       toEntity(TraceDTO traceDTO);

}
