package com.ex.trace.service;
import com.ex.trace.domaine.Message;
import com.ex.trace.domaine.Recommandation;
import com.ex.trace.domaine.Signalement;
import com.ex.trace.domaine.security.Utilisateur;
import com.ex.trace.repository.MessageRepository;
import com.ex.trace.repository.SignalementRepository;
import org.springframework.stereotype.Service;

@Service
public class SignalementService {

    private final MessageRepository messageRepository;
    private final UtilisateurService utilisateurService;
    private final SignalementRepository signalementRepository;


    public SignalementService( MessageRepository messageRepository, UtilisateurService utilisateurService, SignalementRepository signalementRepository) {
        this.messageRepository            = messageRepository;
        this.utilisateurService         = utilisateurService;
        this.signalementRepository    = signalementRepository;
    }


    public void ajouter(long id){
        Message message = messageRepository.findById(id);
        Utilisateur utilisateur         = utilisateurService.obtenirUtilisateurCourant();
        try {
            signalementRepository.save( new Signalement( message, utilisateur) );
        } catch (Exception ex) {
            System.out.println(ex.getCause().getMessage());
        }
    }

    public void supprimer(long id){
        Message message = messageRepository.findById(id);
        Utilisateur utilisateur         = utilisateurService.obtenirUtilisateurCourant();
        try {
            Signalement s =  signalementRepository.findByMessageAndUtilisateur( message, utilisateur);
            signalementRepository.delete(s);
        } catch (Exception ex) {
            System.out.println(ex.getCause().getMessage());
        }
    }

    public void ajouter(Message message){
        Utilisateur utilisateur         = utilisateurService.obtenirUtilisateurCourant();
        try {
            signalementRepository.save( new Signalement( message, utilisateur) );
        } catch (Exception ex) {
            System.out.println(ex.getCause().getMessage());
        }
    }

    public void supprimer(Message message){
        Utilisateur utilisateur         = utilisateurService.obtenirUtilisateurCourant();
        try {
            Signalement s =  signalementRepository.findByMessageAndUtilisateur( message, utilisateur);
            signalementRepository.delete(s);
        } catch (Exception ex) {
            System.out.println(ex.getCause().getMessage());
        }
    }


}
