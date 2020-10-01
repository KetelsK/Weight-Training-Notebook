package com.example.carnetmusculation.Database.PerformanceDB;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.carnetmusculation.Database.DbSchema;
import com.example.carnetmusculation.Models.Performance;

import java.util.UUID;

public class PerformanceCursorWrapper extends CursorWrapper {

    public PerformanceCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Performance getPerformance(){
        String uuidString=getString(getColumnIndex(DbSchema.PerformanceTable.cols.UUID));
        String date=getString(getColumnIndex(DbSchema.PerformanceTable.cols.DATE));
        int poids=getInt(getColumnIndex((DbSchema.PerformanceTable.cols.POIDS)));
        int nbReps=getInt(getColumnIndex((DbSchema.PerformanceTable.cols.NBREPS)));

        Performance performance=new Performance(UUID.fromString(uuidString));
        performance.setDate(date);
        performance.setNbReps(nbReps);
        performance.setPoids(poids);
        return performance;
    }
}

