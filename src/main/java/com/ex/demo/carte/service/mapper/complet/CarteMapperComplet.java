package com.ex.demo.carte.service.mapper.complet;

import com.ex.demo.carte.domaine.Carte;
import com.ex.demo.carte.service.dto.CarteDTO;
import com.ex.demo.util.EntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Carte and its DTO CarteDTO.
 */
@Mapper(componentModel = "spring", uses = {TuileGroupeMapper.class})
public interface CarteMapperComplet extends EntityMapper<CarteDTO, Carte> {



    CarteDTO toDto(Carte carte);

    Carte toEntity(CarteDTO carteDTO);

}
