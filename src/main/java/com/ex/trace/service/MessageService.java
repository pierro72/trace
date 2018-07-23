package com.ex.trace.service;

import com.ex.trace.domaine.*;
import com.ex.trace.domaine.security.Utilisateur;
import com.ex.trace.repository.RecommadationRepository;
import com.ex.trace.repository.SignalementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


public abstract class MessageService {

    @Value( "${trace.visible}")
    protected double distanceVisible;

    @Value( "${trace.lisible}")
    protected double distanceLisible;

    @Autowired
    protected  UtilisateurService utilisateurService;

    @Autowired
    protected SignalementRepository signalementRepository;

    @Autowired
    protected RecommadationRepository recommadationRepository;


    public void ajouterSignalement (Message message){
        Utilisateur utilisateur         = utilisateurService.obtenirUtilisateurCourant();
        try {
            signalementRepository.save( new Signalement( message, utilisateur) );
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

    protected boolean estLisible(double x, double y, Commentaire commentaire  ){
        double distance = obtenirDistance(x, y, commentaire.getTrace().getPositionX(), commentaire.getTrace().getPositionY());
        return (distance < distanceLisible);
    }

    protected boolean estLisible(double x, double y, Trace trace  ){
        double distance = obtenirDistance(x, y, trace.getPositionX(), trace.getPositionY());
        return (distance < distanceLisible);
    }

    protected double obtenirDistance(double x1, double y1, double x2, double y2){
        return java.lang.Math.sqrt ( (x1-x2) * (x1-x2) + (y1-y2) * (y1-y2) );
    }



}
