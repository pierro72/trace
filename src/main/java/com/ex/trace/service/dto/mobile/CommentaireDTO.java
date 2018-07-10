package com.ex.trace.service.dto.mobile;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class CommentaireDTO {

    private Long    id;

    @Size(max = 1024) @Column(length = 1024)  @NotNull
    private String  contenu;

    @NotNull
    private Long    traceId;

    private int     totalLike;

    private Date        date;

    private boolean     autheur;

    private boolean     like;

    private boolean     signale;


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

    public Long getTraceId() {
        return traceId;
    }

    public void setTraceId(Long traceId) {
        this.traceId = traceId;
    }

    public int getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(int totalLike) {
        this.totalLike = totalLike;
    }

    public boolean isAutheur() {
        return autheur;
    }

    public void setAutheur(boolean autheur) {
        this.autheur = autheur;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public boolean isSignale() {
        return signale;
    }

    public void setSignale(boolean signale) {
        this.signale = signale;
    }
}
