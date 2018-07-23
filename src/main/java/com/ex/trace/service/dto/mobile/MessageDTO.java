package com.ex.trace.service.dto.mobile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MessageDTO {

    protected Long      id;

    @Size(max = 1024) @Column (length = 1024)  @NotNull
    private String      contenu;

    @Temporal(TemporalType.DATE)
    private Date        date;

    @NotNull
    private int         totalRecommandation;

    @NotNull
    private int         totalSignalement;

    @NotNull
    private boolean     recommande;

    @NotNull
    private boolean     signale;

    @NotNull
    private boolean     autheur;

    public MessageDTO() {
    }

    public MessageDTO(Long id, String contenu, Date date, int totalRecommandation, int totalSignalement, boolean recommande, boolean signale, boolean autheur) {
        this.id = id;
        this.contenu = contenu;
        this.date = date;
        this.totalRecommandation = totalRecommandation;
        this.totalSignalement = totalSignalement;
        this.recommande = recommande;
        this.signale = signale;
        this.autheur = autheur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTotalRecommandation() {
        return totalRecommandation;
    }

    public void setTotalRecommandation(int totalRecommandation) {
        this.totalRecommandation = totalRecommandation;
    }

    public int getTotalSignalement() {
        return totalSignalement;
    }

    public void setTotalSignalement(int totalSignalement) {
        this.totalSignalement = totalSignalement;
    }

    public boolean isAutheur() {
        return autheur;
    }

    public void setAutheur(boolean autheur) {
        this.autheur = autheur;
    }

    public boolean isRecommande() {
        return recommande;
    }

    public void setRecommande(boolean recommande) {
        this.recommande = recommande;
    }

    public boolean isSignale() {
        return signale;
    }

    public void setSignale(boolean signale) {
        this.signale = signale;
    }
}
