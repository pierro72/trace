package com.ex.trace.service;

import com.ex.trace.domaine.Commentaire;
import com.ex.trace.domaine.Message;
import com.ex.trace.domaine.Recommandation;
import com.ex.trace.domaine.Trace;
import com.ex.trace.domaine.security.Utilisateur;
import com.ex.trace.repository.MessageRepository;
import com.ex.trace.repository.RecommadationRepository;
import com.ex.trace.repository.TraceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommandationService {

    @Autowired
    private TraceRepository traceRepository;


    @Autowired
    private TraceService traceService;

    @Autowired
    private CommentaireService commentaireService;

    private final MessageRepository messageRepository;
    private final UtilisateurService utilisateurService;
    private final RecommadationRepository recommadationRepository;





    public RecommandationService( MessageRepository messageRepository, UtilisateurService utilisateurService, RecommadationRepository recommadationRepository) {
        this.messageRepository            = messageRepository;
        this.utilisateurService         = utilisateurService;
        this.recommadationRepository    = recommadationRepository;
    }

    public void ajouterTrace(long id,  double positionX, double positionY){
        Trace trace             = traceService.obtenirAvecRestrictionPosition( id, positionX, positionY );
        Utilisateur utilisateur = utilisateurService.obtenirUtilisateurCourant();
        try {
            recommadationRepository.save( new Recommandation( trace, utilisateur) );
        } catch (Exception ex) {
            System.out.println(ex.getCause().getMessage());
        }
    }

    public void ajouterCommentaire(long id,  double positionX, double positionY){
        Commentaire commentaire     = commentaireService.afficherAProximite(id, positionX, positionY);
        Utilisateur utilisateur     = utilisateurService.obtenirUtilisateurCourant();
        try {
            recommadationRepository.save( new Recommandation( commentaire, utilisateur) );
        } catch (Exception ex) {
            System.out.println(ex.getCause().getMessage());
        }
    }


    public void ajouter(long id){
        Message message                 = messageRepository.findById(id);
        Utilisateur utilisateur         = utilisateurService.obtenirUtilisateurCourant();
        try {
            recommadationRepository.save( new Recommandation( message, utilisateur) );
        } catch (Exception ex) {
            System.out.println(ex.getCause().getMessage());
        }
    }

    public void supprimer(long id){
        Message message                 = messageRepository.findById(id);
        Utilisateur utilisateur         = utilisateurService.obtenirUtilisateurCourant();
        try {
            Recommandation r =  recommadationRepository.findByMessageAndUtilisateur( message, utilisateur);
            recommadationRepository.delete(r);
        } catch (Exception ex) {
            System.out.println(ex.getCause().getMessage());
        }
    }

    public void ajouter(Message message){
        Utilisateur utilisateur         = utilisateurService.obtenirUtilisateurCourant();
        try {
            recommadationRepository.save( new Recommandation( message, utilisateur) );
        } catch (Exception ex) {
            System.out.println(ex.getCause().getMessage());
        }
    }

    public void supprimer(Message message){

        Utilisateur utilisateur         = utilisateurService.obtenirUtilisateurCourant();
        try {
            Recommandation r =  recommadationRepository.findByMessageAndUtilisateur( message, utilisateur);
            recommadationRepository.delete(r);
        } catch (Exception ex) {
            System.out.println(ex.getCause().getMessage());
        }
    }



}
