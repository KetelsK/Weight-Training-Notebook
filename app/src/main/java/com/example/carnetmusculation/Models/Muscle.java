package com.example.carnetmusculation.Models;

import java.util.ArrayList;
import java.util.UUID;

public class Muscle {
    private ArrayList<Exercice> listeExercices;
    private String nom;
    private UUID uuid;

    public Muscle(){
        this.uuid=UUID.randomUUID();
    }
    public Muscle(String nom){
        this.nom=nom;
        this.uuid= UUID.randomUUID();
        this.listeExercices=new ArrayList<>();
    }

    public Muscle(UUID uuid){
        this.uuid=uuid;
    }

    public void ajouterExercice(Exercice exercice){
        this.listeExercices.add(exercice);
    }

    public ArrayList<Exercice> getListeExercices() {
        return listeExercices;
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
}

