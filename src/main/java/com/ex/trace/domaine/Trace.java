package com.ex.trace.domaine;
import com.ex.trace.TraceType;
import com.ex.trace.domaine.security.Utilisateur;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;


@Entity @Cacheable @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Trace  extends Message{

    @NotNull
    private double      positionX;

    @NotNull
    private double      positionY;

    @NotNull
    private String      codePays;

    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private TraceType   traceType;

    @NotNull
    private int         totalVue;

    //RELATION
    @OneToMany(mappedBy = "trace")
    private Set<Commentaire>    commentaires = new HashSet<>();

    @OneToMany(mappedBy = "trace", cascade={ CascadeType.MERGE, CascadeType.PERSIST })
    private Set<Visite>         visites = new HashSet<>();

    @PrePersist
    private void onCreate() {
        totalVue    = 0;
    }

    public Trace( String contenu, Utilisateur autheur, double positionX, double positionY, String codePays, TraceType traceType ) {
        super ( contenu, autheur);
        this.positionX = positionX;
        this.positionY = positionY;
        this.codePays = codePays;
        this.traceType = traceType;
    }

    public Trace() { }

    public void ajouterVisite (Visite v){
        if (visites == null){
            visites = new HashSet<>();
        }
        visites.add(v);
    }

    public int getTotalVue() {
        return totalVue;
    }

    public void setTotalVue(int totalVue) {
        this.totalVue = totalVue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public TraceType getTraceType() {
        return traceType;
    }

    public void setTraceType(TraceType traceType) {
        this.traceType = traceType;
    }

    public Set<Commentaire> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(Set<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public String getCodePays() {
        return codePays;
    }

    public void setCodePays(String codePays) {
        this.codePays = codePays;
    }

    public Set<Visite> getVisites() {
        return visites;
    }

    public void setVisites(Set<Visite> visites) {
        this.visites = visites;
    }
}
