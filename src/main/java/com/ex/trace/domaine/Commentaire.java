package com.ex.trace.domaine;
import com.ex.trace.domaine.security.Utilisateur;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;

@Entity @Cacheable @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Commentaire extends Message {

    public Commentaire(String contenu, Utilisateur autheur, Trace trace) {
        super(contenu, autheur);
        this.trace = trace;
    }

    public Commentaire() {
    }

    //RELATION
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false)
    private Trace trace;

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


}
