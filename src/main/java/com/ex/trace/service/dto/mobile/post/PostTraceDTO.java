package com.ex.trace.service.dto.mobile.post;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class PostTraceDTO  {

    @Size(min = 2, max = 1024) @Column(length = 1024)  @NotNull
    protected String      contenu;

    @NotNull
    private double      positionX;

    @NotNull
    private double      positionY;

    @NotNull
    private String      codePays;

    public PostTraceDTO(){}

    public PostTraceDTO (String contenu, double positionX, double positionY, String codePays) {
        this.contenu = contenu;
        this.positionX = positionX;
        this.positionY = positionY;
        this.codePays = codePays;
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

    public String getCodePays() {
        return codePays;
    }

    public void setCodePays(String codePays) {
        this.codePays = codePays;
    }
}
