package com.example.carnetmusculation.Models;

import java.io.Serializable;
import java.util.UUID;

public class Performance implements Serializable {
    private UUID uuid;
    private String date;
    private int poids;
    private int nbReps;

    public Performance(){
        this(UUID.randomUUID());
    }

    public Performance(UUID uuid) {
        this.uuid=uuid;
    }

    public UUID getId() {
        return uuid;
    }

    public String getDate() {
        return date;
    }

    public int getPoids() {
        return poids;
    }

    public int getNbReps() {
        return nbReps;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }

    public void setNbReps(int nbReps) {
        this.nbReps = nbReps;
    }
}

