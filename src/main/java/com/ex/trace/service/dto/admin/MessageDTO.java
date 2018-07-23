package com.ex.trace.service.dto.admin;

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
    private boolean     estDouteux;

    @NotNull
    private boolean     estVerifier;

    @NotNull
    private int         totalLike;

    @NotNull
    private int         totalSignalement;

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

    public int getTotalSignalement() {
        return totalSignalement;
    }

    public void setTotalSignalement(int totalSignalement) {
        this.totalSignalement = totalSignalement;
    }
}
