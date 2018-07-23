package com.ex.trace.controller;

import com.ex.trace.domaine.security.Utilisateur;
import com.ex.trace.service.UtilisateurService;
import com.ex.trace.service.dto.mobile.UtilisateurInscriptionDTO;
import com.ex.trace.service.mapper.UtilisateurMapper;
import com.ex.trace.validator.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;

/**
 * REST controller for managing Commentaire.
 */
@RestController
@RequestMapping("/inscription")
public class InscriptionController {

    @Autowired private UtilisateurService utilisateurService;
    @Autowired private UtilisateurMapper utilisateurMapper;

    public InscriptionController(){ }

    @PostMapping("/utilisateur")
    @ResponseStatus(HttpStatus.CREATED)
    public void enregistrerCompteUtilisateur(@Valid @RequestBody UtilisateurInscriptionDTO utilisateurDTO )throws URISyntaxException {

        Utilisateur utilisateurMobileInscritpion = utilisateurMapper.toEntity(utilisateurDTO);
        Utilisateur utilisateurMobile = null;
        try {
            utilisateurMobile =  utilisateurService.enregistrerUtilisateurMobile( utilisateurMobileInscritpion );
        } catch (EmailExistsException e) {
        }


    }




}
