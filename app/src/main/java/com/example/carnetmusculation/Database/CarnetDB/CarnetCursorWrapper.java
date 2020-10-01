package com.example.carnetmusculation.Database.CarnetDB;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.carnetmusculation.Database.DbSchema;
import com.example.carnetmusculation.Models.Carnet;

import java.util.UUID;

public class CarnetCursorWrapper extends CursorWrapper {

    public CarnetCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Carnet getCarnet(){
        String uuidString=getString(getColumnIndex(DbSchema.CarnetsTable.cols.UUID));
        String nom=getString(getColumnIndex(DbSchema.CarnetsTable.cols.NOM));

        Carnet carnet=new Carnet(UUID.fromString(uuidString));
        carnet.setNom(nom);
        return carnet;
    }

    public String getNomCarnet(){
        String nomCarnet=getString(getColumnIndex(DbSchema.CarnetsTable.cols.NOM));
        return nomCarnet;
    }
}
