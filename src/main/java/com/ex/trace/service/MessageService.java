package com.ex.trace.service;

import com.ex.trace.domaine.*;
import com.ex.trace.domaine.security.Utilisateur;
import com.ex.trace.exception.ResourceNotFoundException;
import com.ex.trace.repository.MessageRepository;
import com.ex.trace.repository.RecommadationRepository;
import com.ex.trace.repository.SignalementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


public abstract class MessageService {

    @Value( "${trace.visible}") protected double VISIBLE;
    @Value( "${trace.lisible}") protected double LISIBLE;
    @Autowired protected MessageRepository messageRepository;
    @Autowired protected UtilisateurService utilisateurService;
    @Autowired protected SignalementRepository signalementRepository;
    @Autowired protected RecommadationRepository recommadationRepository;


    public Message obtenir (long id){
        Message message = messageRepository.findOne(id);
        if ( message == null) {
            throw new ResourceNotFoundException( Message.class.getSimpleName(), id);
        }
        return message;
    }

    public void ajouterSignalement (Message message){
        Utilisateur utilisateur         = utilisateurService.obtenirUtilisateurCourant();
        try {
            Signalement s = new Signalement( message, utilisateur);
            signalementRepository.save( s );
        } catch (Exception ex) {
            System.out.println(ex.getCause().getMessage());
        }
    }

    public void supprimerSignalement (Message message){
        Utilisateur utilisateur         = utilisateurService.obtenirUtilisateurCourant();
        try {
            Signalement s =  signalementRepository.findByMessageAndUtilisateur( message, utilisateur);
            signalementRepository.delete(s);
        } catch (Exception ex) {
            System.out.println(ex.getCause().getMessage());
        }
    }

    public void ajouterRecommandation (Message message){
        Utilisateur utilisateur         = utilisateurService.obtenirUtilisateurCourant();
        try {
            recommadationRepository.save( new Recommandation( message, utilisateur) );
        } catch (Exception ex) {
            System.out.println(ex.getCause().getMessage());
        }
    }

    public void supprimerRecommandation (Message message){

        Utilisateur utilisateur         = utilisateurService.obtenirUtilisateurCourant();
        try {
            Recommandation r =  recommadationRepository.findByMessageAndUtilisateur( message, utilisateur);
            recommadationRepository.delete(r);
        } catch (Exception ex) {
            System.out.println(ex.getCause().getMessage());
        }
    }

    public void valider (long id, boolean valide){
        Message message = obtenir( id);
        message.setEstVerifier( valide );
        messageRepository.save( message );
    }

    public void supprimer ( long id) {
        Message message = obtenir( id);
        messageRepository.delete(id);
    }

    protected boolean estLisible(double x, double y, Commentaire commentaire  ){
        double distance = obtenirDistance(x, y, commentaire.getTrace().getPositionX(), commentaire.getTrace().getPositionY());
        return (distance < LISIBLE);
    }

    protected boolean estLisible(double x, double y, Trace trace  ){
        double distance = obtenirDistance(x, y, trace.getPositionX(), trace.getPositionY());
        return (distance < LISIBLE);
    }

    protected double obtenirDistance(double x1, double y1, double x2, double y2){
        return java.lang.Math.sqrt ( (x1-x2) * (x1-x2) + (y1-y2) * (y1-y2) );
    }



}
