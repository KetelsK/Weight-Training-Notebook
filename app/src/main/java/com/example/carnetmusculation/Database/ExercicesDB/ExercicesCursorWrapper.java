package com.example.carnetmusculation.Database.ExercicesDB;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.carnetmusculation.Database.DbSchema;
import com.example.carnetmusculation.Models.Exercice;

import java.util.UUID;

public class ExercicesCursorWrapper extends CursorWrapper {
    public ExercicesCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Exercice getExercice(){
        String uuidString=getString(getColumnIndex(DbSchema.ExercicesTable.cols.UUID));
        String nom=getString(getColumnIndex(DbSchema.ExercicesTable.cols.NOM));

        Exercice exercice=new Exercice(UUID.fromString(uuidString));
        exercice.setNom(nom);
        return exercice;
    }

    public String getNomExercice() {
        String nomExercice=getString(getColumnIndex(DbSchema.ExercicesTable.cols.NOM));
        return nomExercice;
    }
}

