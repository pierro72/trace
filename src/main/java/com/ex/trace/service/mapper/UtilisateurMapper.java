package com.ex.trace.service.mapper;

import com.ex.trace.domaine.security.Utilisateur;
import com.ex.trace.service.UtilisateurService;
import com.ex.trace.service.dto.mobile.post.UtilisateurInscriptionDTO;
import com.ex.trace.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


/**
 * Mapper for the entity Utilisateur and its DTO UtilisateurInscriptionDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { UtilisateurService.class })
public interface UtilisateurMapper extends EntityMapper<UtilisateurInscriptionDTO, Utilisateur> {

    UtilisateurInscriptionDTO toDto( Utilisateur utilisateur);

}
