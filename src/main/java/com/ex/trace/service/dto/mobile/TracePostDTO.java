package com.ex.trace.service.dto.mobile;


import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TracePostDTO {


    @Size(max = 1024) @Column(length = 1024)  @NotNull
    private String      contenu;

    @NotNull
    private double      positionX;

    @NotNull
    private double      positionY;


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

}
