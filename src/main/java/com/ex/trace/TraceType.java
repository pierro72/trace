package com.py.trace;

public enum TraceType {

    I("Info"),
    M("Message");

    private String nom ="";

    //Constructeur
    TraceType(String name){
        this.nom = name;
    }

    public String toString(){
        return nom;
    }

}
