package com.ex.demo.carte;

public enum TuileType {

    T("Terrrestre"),
    M("Mritime");

    private String nom ="";

    //Constructeur
    TuileType(String name){
        this.nom = name;
    }

    public String toString(){
        return nom;
    }

}
