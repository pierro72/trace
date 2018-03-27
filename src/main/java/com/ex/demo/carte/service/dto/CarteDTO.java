package com.ex.demo.carte.service.dto;
import java.util.Set;

public class CarteDTO {

    private Long id;

    private String nom;

    private String description;

    private Set<TuileGroupeDTO> tuileGroupes;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<TuileGroupeDTO> getTuileGroupes() {
        return tuileGroupes;
    }

    public void setTuileGroupes(Set<TuileGroupeDTO> tuileGroupes) {
        this.tuileGroupes = tuileGroupes;
    }
}
