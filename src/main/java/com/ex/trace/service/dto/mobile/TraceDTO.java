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

    private Long id;

    @Size(max = 1024) @Column(length = 1024)  @NotNull
    private String contenu;

    @NotNull
    private double positionX;

    @NotNull
    private double positionY;

    @NotNull
    private TraceType traceType;

    private Date date;

    private int vue;


    private Set<CommentaireDTO> commentaires = new HashSet<>();

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

    public Set<CommentaireDTO> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(Set<CommentaireDTO> commentaires) {
        this.commentaires = commentaires;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getVue() {
        return vue;
    }

    public void setVue(int vue) {
        this.vue = vue;
    }
}
