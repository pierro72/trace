package com.ex.trace.service.dto.admin;
import com.ex.trace.TraceType;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TraceDTO {

    private Long id;

    @Size(max = 1024) @Column(length = 1024)  @NotNull
    private String      contenu;

    @NotNull
    private double      positionX;

    @NotNull
    private double      positionY;

    @NotNull
    private TraceType   traceType;

    @NotNull
    private Date        date;

    @NotNull
    private boolean     estDouteux;

    @NotNull
    private boolean     estVerifier;

    @NotNull
    private int         totalVue;

    @NotNull
    private int         totalLike;

    @NotNull
    private String      codePays;

    @NotNull
    private Long        autheurId;


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

    public boolean isEstDouteux() {
        return estDouteux;
    }

    public void setEstDouteux(boolean estDouteux) {
        this.estDouteux = estDouteux;
    }

    public boolean isEstVerifier() {
        return estVerifier;
    }

    public void setEstVerifier(boolean estVerifier) {
        this.estVerifier = estVerifier;
    }

    public String getCodePays() {
        return codePays;
    }

    public void setCodePays(String codePays) {
        this.codePays = codePays;
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

    public Long getAutheurId() {
        return autheurId;
    }

    public void setAutheurId(Long autheurId) {
        this.autheurId = autheurId;
    }
}
