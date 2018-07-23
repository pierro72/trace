package com.ex.trace.service.dto.admin;
import com.ex.trace.TraceType;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TraceDTO extends  MessageDTO {

    @NotNull
    private double      positionX;

    @NotNull
    private double      positionY;

    @NotNull
    private TraceType   traceType;

    @NotNull
    private int         totalVue;

    @NotNull
    private int         totalLike;

    @NotNull
    private String      codePays;


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

}
