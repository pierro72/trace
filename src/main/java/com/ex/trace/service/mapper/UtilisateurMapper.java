package com.ex.trace.service.mapper;

import com.ex.trace.domaine.Utilisateur;
import com.ex.trace.service.UtilisateurService;
import com.ex.trace.service.dto.mobile.UtilisateurInscriptionDTO;
import com.ex.trace.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Mapper for the entity Utilisateur and its DTO UtilisateurInscriptionDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { UtilisateurService.class })
public interface UtilisateurMapper extends EntityMapper<UtilisateurInscriptionDTO, Utilisateur> {

    UtilisateurInscriptionDTO toDto(Utilisateur utilisateur);

}
