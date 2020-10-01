package com.example.carnetmusculation.Models;

import java.io.Serializable;
import java.util.UUID;

public class Seance implements Serializable {
    private String nom;
    private UUID uuid;

    public Seance(){
        this(UUID.randomUUID());
    }

    public Seance(UUID uuid){
        this.uuid=uuid;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public UUID getId() {
        return uuid;
    }

    public void setId(UUID uuid) {
        this.uuid = uuid;
    }
}
