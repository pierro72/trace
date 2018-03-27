package com.ex.demo.test.domaine;

import javax.persistence.Entity;

@Entity
public class Chien extends Animal {

    public String prenom;

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
