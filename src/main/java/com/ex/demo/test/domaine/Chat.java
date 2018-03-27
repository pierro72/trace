package com.ex.demo.test.domaine;

import com.ex.demo.test.domaine.Animal;

import javax.persistence.Entity;

@Entity
public class Chat extends Animal {

    public String nom;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
