package com.ex.trace.service.dto.mobile;
import javax.validation.constraints.NotNull;


public class PostTraceDTO extends PostMessageDTO {


    @NotNull
    private double      positionX;

    @NotNull
    private double      positionY;

    @NotNull
    private String      codePays;


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
