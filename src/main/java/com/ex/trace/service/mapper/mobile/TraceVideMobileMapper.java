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
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TraceVideMobileMapper extends EntityMapper<TraceDTO, Trace> {

    @Mapping( target = "contenu",       ignore = true )
    @Mapping( target = "commentaires",  ignore = true )
    @Mapping( target = "date",          ignore = true )
    @Mapping( target = "vue",          ignore = true )
    TraceDTO    toDto(Trace trace);


}
