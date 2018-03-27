package com.ex.demo.carte;

public enum VilleType {

    P("Petite"),
    M("Moyenne"),
    G("Grande");

    private String nom ="";

    //Constructeur
    VilleType(String name){
        this.nom = name;
    }

    public String toString(){
        return nom;
    }

}
