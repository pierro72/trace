package com.ex.demo.carte.service.dto;

import com.ex.demo.carte.RessourceType;
import com.ex.demo.carte.TuileType;


public class TuileDTO {

    private Long tuileGroupeId;

    private Long id;

    private TuileType tuileType;

    private Integer positionX;

    private Integer positionY;

    private Boolean limite;

    private Boolean capitale;

    private RessourceType ressourceType;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TuileType getTuileType() {
        return tuileType;
    }

    public void setTuileType(TuileType tuileType) {
        this.tuileType = tuileType;
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

    public Boolean getLimite() {
        return limite;
    }

    public void setLimite(Boolean limite) {
        this.limite = limite;
    }

    public Boolean getCapitale() {
        return capitale;
    }

    public void setCapitale(Boolean capitale) {
        this.capitale = capitale;
    }

    public Long getTuileGroupeId() {
        return tuileGroupeId;
    }

    public void setTuileGroupeId(Long tuileGroupeId) {
        this.tuileGroupeId = tuileGroupeId;
    }

    public RessourceType getRessourceType() {
        return ressourceType;
    }

    public void setRessourceType(RessourceType ressourceType) {
        this.ressourceType = ressourceType;
    }
}
