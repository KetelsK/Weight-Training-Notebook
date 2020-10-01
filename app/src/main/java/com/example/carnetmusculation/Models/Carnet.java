package com.example.carnetmusculation.Models;

import java.io.Serializable;
import java.util.UUID;

public class Carnet implements Serializable {
    private String nom;
    private UUID uuid;

    public Carnet(){
        this(UUID.randomUUID());
    }

    public Carnet(UUID uuid){
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

