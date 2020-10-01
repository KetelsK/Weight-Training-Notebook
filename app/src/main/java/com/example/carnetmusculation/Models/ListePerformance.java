package com.example.carnetmusculation.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.carnetmusculation.Database.DbSchema;
import com.example.carnetmusculation.Database.PerformanceDB.PerformanceCursorWrapper;
import com.example.carnetmusculation.Database.PerformanceDB.PerformanceDbHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListePerformance {
    private Context context;
    private SQLiteDatabase dataBase;
    private static ListePerformance listePerformance;

    public static ListePerformance get(Context context){
        if(listePerformance ==null){
            listePerformance =new ListePerformance(context);
        }
        return listePerformance;
    }

    private ListePerformance(Context context){
        this.context=context.getApplicationContext();
        this.dataBase=new PerformanceDbHelper(context).getWritableDatabase();
    }

    public void ajouterPerformance(Performance performance,UUID uuid){
        this.dataBase.insert(DbSchema.PerformanceTable.NAME,null,getContentValues(performance,uuid));
    }

    public void supprimerPerformance(Performance performance){
        this.dataBase.delete(DbSchema.PerformanceTable.NAME,DbSchema.PerformanceTable.cols.UUID+"=?",new String[]{
                String.valueOf(performance.getId())
        });
    }

    public void updatePerformance(Performance performance,UUID uuidExercice){
        UUID uuidPerformance=performance.getId();

        ContentValues values = getContentValues(performance,uuidExercice);

        dataBase.update(DbSchema.PerformanceTable.NAME, values, DbSchema.ExercicesTable.cols.UUID + " = ?", new String[] {String.valueOf(uuidPerformance)});
    }

    private ContentValues getContentValues(Performance performance, UUID uuid){
        ContentValues values = new ContentValues();
        values.put(DbSchema.PerformanceTable.cols.UUID,performance.getId().toString());
        values.put(DbSchema.PerformanceTable.cols.DATE,performance.getDate().toString());
        values.put(DbSchema.PerformanceTable.cols.POIDS,performance.getPoids());
        values.put(DbSchema.PerformanceTable.cols.NBREPS,performance.getNbReps());
        values.put(DbSchema.PerformanceTable.cols.UUIDEXERCICE,uuid.toString());
        return values;
    }

    private PerformanceCursorWrapper queryPerformance(String whereClause, String[] whereArgs){
        return new PerformanceCursorWrapper(dataBase.query(DbSchema.PerformanceTable.NAME, null, whereClause,whereArgs,null,null,null));
    }

    public List<Performance> getPerformances(UUID uuid){
        ArrayList<Performance> performances= new ArrayList<>();

        PerformanceCursorWrapper cursor = queryPerformance(DbSchema.PerformanceTable.cols.UUIDEXERCICE +"= '"+uuid+"'",null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                performances.add(cursor.getPerformance());
                cursor.moveToNext();
            }
        }finally{
            cursor.close();
        }
        return performances;
    }

    public Performance getPerformance(UUID id) {
        PerformanceCursorWrapper cursor = queryPerformance(DbSchema.PerformanceTable.cols.UUID+ " = ? ", new String[] {id.toString()});
        try{
            if(cursor.getCount() == 0)
                return null;
            cursor.moveToFirst();
            return cursor.getPerformance();
        }finally{
            cursor.close();
        }
    }
}

