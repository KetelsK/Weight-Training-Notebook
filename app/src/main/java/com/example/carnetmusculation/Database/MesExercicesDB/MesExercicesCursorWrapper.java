package com.example.carnetmusculation.Database.MesExercicesDB;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.carnetmusculation.Database.DbSchema;
import com.example.carnetmusculation.Models.Exercice;

import java.util.UUID;

public class MesExercicesCursorWrapper extends CursorWrapper {

    public MesExercicesCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Exercice getExercice(){
        String uuidString=getString(getColumnIndex(DbSchema.MesExercicesTable.cols.UUID));
        String nom=getString(getColumnIndex(DbSchema.MesExercicesTable.cols.NOM));

        Exercice exercice=new Exercice(UUID.fromString(uuidString));
        exercice.setNom(nom);
        return exercice;
    }
}

