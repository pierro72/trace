package com.ex.trace.domaine;

import com.ex.trace.domaine.CompositePK.UtilisateurMessageCompositePK;
import com.ex.trace.domaine.security.Utilisateur;
import javax.persistence.*;

@Entity
public class Recommandation {

    @EmbeddedId
    private UtilisateurMessageCompositePK idpk;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn( name = "id_message", nullable = false, insertable = false, updatable = false)
    private Message       message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "id_utilisateur", nullable = false, insertable = false, updatable = false)
    private Utilisateur utilisateur;


    public Recommandation(){}

    public Recommandation(Message message, Utilisateur utilisateur){
        this.message = message;
        this.utilisateur = utilisateur;
    }

    public Recommandation(Trace trace, Utilisateur utilisateur){
        this.message = trace;
        this.utilisateur = utilisateur;
    }

    public Recommandation(Commentaire commentaire, Utilisateur utilisateur){
        this.message = commentaire;
        this.utilisateur = utilisateur;
    }

    @PrePersist
    private void onCreate() {
        this.idpk = new UtilisateurMessageCompositePK(utilisateur.getId(), message.getId() );
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public UtilisateurMessageCompositePK getId() {
        return idpk;
    }

    public void setId(UtilisateurMessageCompositePK id) {
        this.idpk = id;
    }




}



