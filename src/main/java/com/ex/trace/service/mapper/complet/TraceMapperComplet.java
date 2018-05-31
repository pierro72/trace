package com.py.trace.service.mapper.complet;


import com.py.trace.domaine.Trace;
import com.py.trace.service.dto.TraceDTO;
import com.py.trace.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;

/**
 * Mapper for the entity Trace and its DTO TraceDTO.
 */
@MapperConfig(componentModel = "spring")
public interface TraceMapperComplet extends EntityMapper<TraceDTO, Trace> {

    TraceDTO    toDto(Trace trace);

    Trace       toEntity(TraceDTO traceDTO);

}
