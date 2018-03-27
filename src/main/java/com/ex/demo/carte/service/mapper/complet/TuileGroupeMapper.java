package com.ex.demo.carte.service.mapper.complet;

import com.ex.demo.carte.domaine.TuileGroupe;
import com.ex.demo.carte.service.dto.TuileGroupeDTO;
import com.ex.demo.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity TuileGroupe and its DTO TuileGroupeDTO.
 */
@Mapper(componentModel = "spring", uses = {TuileMapper.class})
public interface TuileGroupeMapper extends EntityMapper<TuileGroupeDTO, TuileGroupe> {


    @Mapping(source="carte.id", target="carteId")
    TuileGroupeDTO toDto(TuileGroupe tuile);

    @Mapping(source="carteId", target="carte.id")
    TuileGroupe toEntity(TuileGroupeDTO tuileGroupeDTO);

}
