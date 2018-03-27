package com.ex.demo.carte.service.mapper.simple;


import com.ex.demo.carte.domaine.Carte;
import com.ex.demo.carte.service.dto.CarteDTO;
import com.ex.demo.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Carte and its DTO CarteDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CarteMapper extends EntityMapper<CarteDTO, Carte> {


    @Mapping(target = "tuileGroupes", ignore = true)
    CarteDTO toDto(Carte carte);

    @Mapping(target = "tuileGroupes", ignore = true)
    Carte toEntity(CarteDTO carteDTO);

}
