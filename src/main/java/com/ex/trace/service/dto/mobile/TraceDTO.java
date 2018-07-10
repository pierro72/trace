package com.ex.trace.service.dto.mobile;
import com.ex.trace.TraceType;
import com.ex.trace.service.dto.admin.CommentaireDTO;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TraceDTO {

    private Long        id;

    @Size(max = 1024) @Column(length = 1024)  @NotNull
    private String      contenu;

    @NotNull
    private double      positionX;

    @NotNull
    private double      positionY;

    @NotNull
    private TraceType   traceType;

    private Date        date;

    private int         totalVue;

    private int         totalLike;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTotalVue() {
        return totalVue;
    }

    public void setTotalVue(int totalVue) {
        this.totalVue = totalVue;
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
