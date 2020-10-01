package com.example.carnetmusculation.Models;

import java.io.Serializable;
import java.util.UUID;

public class Exercice implements Serializable {
    private String nom;
    private UUID uuid;

    public Exercice(String nom){
        this(UUID.randomUUID());
        this.nom=nom;
    }

    public Exercice(){
        this(UUID.randomUUID());
    }
    public Exercice(UUID uuid){
        this.uuid=uuid;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom){
        this.nom=nom;
    }

    public UUID getId() {
        return uuid;
    }
}
