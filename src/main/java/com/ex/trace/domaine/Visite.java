package com.ex.trace.domaine;

import com.ex.trace.domaine.CompositePK.UtilisateurTraceCompositePK;
import com.ex.trace.domaine.security.Utilisateur;
import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Visite {

    public Visite(){}

    public Visite(Trace trace, Utilisateur utilisateur){
        this.trace = trace;
        this.utilisateur = utilisateur;
    }

    @EmbeddedId
    private UtilisateurTraceCompositePK id;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn( name = "id_trace", nullable = false, insertable = false, updatable = false)
    private Trace       trace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "id_utilisateur", nullable = false, insertable = false, updatable = false)
    private Utilisateur utilisateur;

    @PrePersist
    private void onCreate() {
        this.id = new UtilisateurTraceCompositePK(utilisateur.getId(), trace.getId() );
    }

    public UtilisateurTraceCompositePK getId() {
        return id;
    }

    public void setId(UtilisateurTraceCompositePK id) {
        this.id = id;
    }

    public Trace getTrace() {
        return trace;
    }

    public void setTrace(Trace trace) {
        this.trace = trace;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}



