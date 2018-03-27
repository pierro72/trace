package com.ex.demo.carte.domaine;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Carte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "nom", length = 50, nullable = false)
    private String nom;

    @Size(max = 1024)
    @Column(name = "description", length = 1024)
    private String description;

    @OneToMany(mappedBy = "carte")
    private Set<TuileGroupe> tuileGroupes = new HashSet<>();

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

    public Set<TuileGroupe> getTuileGroupes() {
        return tuileGroupes;
    }

    public void setTuileGroupes(Set<TuileGroupe> tuileGroupes) {
        this.tuileGroupes = tuileGroupes;
    }
}
