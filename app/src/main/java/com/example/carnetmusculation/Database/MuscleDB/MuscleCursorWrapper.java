package com.example.carnetmusculation.Database.MuscleDB;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.carnetmusculation.Database.DbSchema;
import com.example.carnetmusculation.Models.Muscle;

import java.util.UUID;

public class MuscleCursorWrapper extends CursorWrapper {

    public MuscleCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Muscle getMuscle(){
        String uuidString=getString(getColumnIndex(DbSchema.MuscleTable.cols.UUID));
        String nom=getString(getColumnIndex(DbSchema.MuscleTable.cols.NOM));

        Muscle muscle=new Muscle(UUID.fromString(uuidString));
        muscle.setNom(nom);
        return muscle;
    }
}

