package com.ex.demo.carte.domaine;

import com.ex.demo.carte.RessourceType;
import com.ex.demo.carte.TuileType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tuile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private TuileType tuileType;

    @NotNull
    private Integer positionX;

    @NotNull
    private Integer positionY;

    @NotNull
    private  Boolean limite;

    @NotNull
    private Boolean capitale;

    @NotNull
    private RessourceType ressourceType;


    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn( nullable = false)
    private TuileGroupe tuileGroupe;

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

    public TuileGroupe getTuileGroupe() {
        return tuileGroupe;
    }

    public void setTuileGroupe(TuileGroupe tuileGroupe) {
        this.tuileGroupe = tuileGroupe;
    }

    public Boolean getCapitale() {
        return capitale;
    }

    public void setCapitale(Boolean capitale) {
        this.capitale = capitale;
    }

    public RessourceType getRessourceType() {
        return ressourceType;
    }

    public void setRessourceType(RessourceType ressourceType) {
        this.ressourceType = ressourceType;
    }
}
