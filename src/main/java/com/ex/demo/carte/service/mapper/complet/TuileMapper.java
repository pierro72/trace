package com.ex.demo.carte.service.mapper.complet;

import com.ex.demo.carte.domaine.Tuile;
import com.ex.demo.carte.service.dto.TuileDTO;
import com.ex.demo.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Tuile and its DTO TuileDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TuileMapper extends EntityMapper<TuileDTO, Tuile> {


    @Mapping(source="tuileGroupe.id", target="tuileGroupeId")
    TuileDTO toDto(Tuile tuile);


    @Mapping(source="tuileDTO.tuileGroupeId", target="tuileGroupe.id")
    Tuile toEntity(TuileDTO tuileDTO);

}
