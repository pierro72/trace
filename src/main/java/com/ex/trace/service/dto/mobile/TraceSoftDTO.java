package com.ex.trace.service.dto.mobile;

import com.ex.trace.TraceType;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class TraceSoftDTO {

    private Long        id;

    @NotNull
    private double      positionX;

    @NotNull
    private double      positionY;

    @NotNull
    private TraceType   traceType;

    private boolean     autheur;

    private boolean     vue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isAutheur() {
        return autheur;
    }

    public void setAutheur(boolean autheur) {
        this.autheur = autheur;
    }

    public boolean isVue() {
        return vue;
    }

    public void setVue(boolean vue) {
        this.vue = vue;
    }

}
