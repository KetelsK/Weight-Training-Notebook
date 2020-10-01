package com.example.carnetmusculation.Database.SeancesDB;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.carnetmusculation.Database.DbSchema;
import com.example.carnetmusculation.Models.Seance;

import java.util.UUID;

public class SeancesCursorWrapper extends CursorWrapper {
    public SeancesCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Seance getSeance(){
        String uuidString=getString(getColumnIndex(DbSchema.SeancesTable.cols.UUID));
        String nom=getString(getColumnIndex(DbSchema.SeancesTable.cols.NOM));

        Seance seance=new Seance(UUID.fromString(uuidString));
        seance.setNom(nom);
        return seance;
    }

    public String getNomSeance() {
        String nomSeance=getString(getColumnIndex(DbSchema.SeancesTable.cols.NOM));
        return nomSeance;
    }
}

