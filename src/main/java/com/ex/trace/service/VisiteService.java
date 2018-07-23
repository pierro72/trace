package com.ex.trace.service;
import com.ex.trace.domaine.Message;
import com.ex.trace.domaine.Signalement;
import com.ex.trace.domaine.Trace;
import com.ex.trace.domaine.Visite;
import com.ex.trace.domaine.security.Utilisateur;
import com.ex.trace.exception.TraceNotProxiException;
import com.ex.trace.repository.MessageRepository;
import com.ex.trace.repository.SignalementRepository;
import com.ex.trace.repository.VisiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisiteService {

    @Autowired
    VisiteRepository visiteRepository;
    private final UtilisateurService utilisateurService;


    public VisiteService(UtilisateurService utilisateurService) {
        this.utilisateurService         = utilisateurService;
    }


    public void ajouter(Trace trace){
        Utilisateur utilisateur         = utilisateurService.obtenirUtilisateurCourant();
        try {
            visiteRepository.save(new Visite( trace, utilisateur));
        } catch (Exception ex) {
            System.out.println(ex.getCause().getMessage());
        }
    }


}
