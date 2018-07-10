package com.ex.trace.domaine;

import com.ex.trace.domaine.security.Utilisateur;
import javax.persistence.*;

@Entity
public class TraceVue {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long        id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false)
    private Trace       trace;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false)
    private Utilisateur utilisateur;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
