package com.ex.trace.domaine;
import com.ex.trace.TraceType;
import com.ex.trace.domaine.security.Utilisateur;
import com.ex.trace.security.JwtUtilisateur;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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

    @Transient
    private int         totalVisite;

    @Transient
    private boolean     vue;

    //RELATION
    @OneToMany(mappedBy = "trace")
    private Set<Commentaire>    commentaires = new HashSet<>();

    @OneToMany(mappedBy = "trace", cascade={ CascadeType.MERGE, CascadeType.PERSIST })
    private Set<Visite>         visites = new HashSet<>();

    @PostLoad
    private void postLoadTrace() {
        JwtUtilisateur utilisateur = (JwtUtilisateur)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.totalVisite = this.getVisites().size();
        this.vue         = this.estVue( utilisateur );
    }

    //CONSTRUCTEUR
    public Trace( String contenu, Utilisateur autheur, double positionX, double positionY, String codePays, TraceType traceType ) {
        super ( contenu, autheur);
        this.positionX = positionX;
        this.positionY = positionY;
        this.codePays = codePays;
        this.traceType = traceType;
    }

    public Trace() { }

    //METHODE
    public void ajouterVisite (Visite v){
        if (visites == null){
            visites = new HashSet<>();
        }
        visites.add(v);
    }

    private boolean estVue (  JwtUtilisateur utilisateur){
        boolean estVue = false;
        for ( Visite v : this.getVisites()) {
            if ( v.getUtilisateur().getId().longValue() == utilisateur.getId().longValue() ){
                estVue = true;
                break;
            }
        }
        return estVue;
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

    public int getTotalVisite() {
        return totalVisite;
    }

    public void setTotalVisite(int totalVisite) {
        this.totalVisite = totalVisite;
    }

    public boolean isVue() {
        return vue;
    }

    public void setVue(boolean vue) {
        this.vue = vue;
    }
}
