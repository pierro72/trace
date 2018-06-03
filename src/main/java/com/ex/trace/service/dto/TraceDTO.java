package com.ex.trace.service.dto;
import com.ex.trace.TraceType;
import java.util.HashSet;
import java.util.Set;

public class TraceDTO {

    private Long id;

    private String contenu;

    private Integer positionX;

    private Integer positionY;

    private TraceType traceType;

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

    public Integer getPositionX() {
        return positionX;
    }

    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    public Integer getPositionY() {
        return positionY;
    }

    public void setPositionY(Integer positionY) {
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
}
