package com.ex.demo.carte.service.dto;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the TuileGroupe entity.
 */
public class TuileGroupeDTO implements Serializable {

    private Long carteId;

    private Long id;

    private String nom;

    private List<TuileDTO> tuiles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<TuileDTO> getTuiles() {
        return tuiles;
    }

    public void setTuiles(List<TuileDTO> tuiles) {
        this.tuiles = tuiles;
    }

    public Long getCarteId() {
        return carteId;
    }

    public void setCarteId(Long carteId) {
        this.carteId = carteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TuileGroupeDTO tuileGroupeDTO = (TuileGroupeDTO) o;
        if(tuileGroupeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tuileGroupeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TuileGroupeDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
