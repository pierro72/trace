package com.ex.demo.carte;

public enum RessourceType {

    N("Pas de ressource"),
    V("Ville"),
    O("Or"),
    B("Bois"),
    P("Pierre"),
    F("Fer");

    private String nom ="";

    //Constructeur
    RessourceType(String name){
        this.nom = name;
    }

    public String toString(){
        return nom;
    }

}
