package com.ex.trace.service.dto.mobile;
import com.ex.trace.TraceType;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class TraceDTO extends MessageDTO {

    @NotNull
    private TraceType   traceType;

    private int         totalVisite;

    @NotNull
    private double      positionX;

    @NotNull
    private double      positionY;


    public TraceDTO(){ }


    public TraceDTO(Long id, String contenu, Date date, int totalRecommandation, int totalSignalement, boolean recommande, boolean signale, boolean autheur, TraceType traceType, int totalVisite, double positionX, double positionY) {
        super (id, contenu, date, totalRecommandation, totalSignalement, recommande, signale, autheur );
        this.traceType = traceType;
        this.totalVisite = totalVisite;
        this.positionX = positionX;
        this.positionY = positionY;
    }



    public TraceType getTraceType() {
        return traceType;
    }

    public void setTraceType(TraceType traceType) {
        this.traceType = traceType;
    }

    public int getTotalVisite() {
        return totalVisite;
    }

    public void setTotalVisite(int totalVisite) {
        this.totalVisite = totalVisite;
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
