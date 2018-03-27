package com.ex.demo.test.service.mapper.complet;

import com.ex.demo.test.domaine.Animal;
import com.ex.demo.test.domaine.Chat;
import com.ex.demo.test.domaine.Chien;
import com.ex.demo.test.service.dto.AnimalDTO;
import com.ex.demo.test.service.dto.ChatDTO;
import com.ex.demo.test.service.dto.ChienDTO;
import com.ex.demo.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Animal and its DTO AnimalDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AnimalMapper extends EntityMapper<AnimalDTO, Animal> {



    default AnimalDTO toDto(Animal animal) {
        if ( animal instanceof Chat){
            return toDto((Chat)animal);
        } else {
            return   toDto((Chien)animal);
        }
    }

    default Animal toEntity(AnimalDTO animalDTO){
        if ( animalDTO instanceof ChatDTO){
            return toEntity( (ChatDTO) animalDTO);

        } else {
            return toEntity( (ChienDTO) animalDTO);
        }
    }


    ChatDTO toDto(Chat chat);

    Chat toEntity(ChatDTO ChatDTO);

    ChienDTO toDto(Chien chien);

    Chien toEntity(ChienDTO ChienDTO);

}
