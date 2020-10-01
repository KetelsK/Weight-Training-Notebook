package com.example.carnetmusculation.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.carnetmusculation.Database.DbSchema;
import com.example.carnetmusculation.Database.ExercicesDB.ExercicesCursorWrapper;
import com.example.carnetmusculation.Database.ExercicesDB.ExercicesDbHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListeExercice implements Serializable {
    private Context context;
    private SQLiteDatabase dataBase;
    private static ListeExercice listeExercice;

    public static ListeExercice get(Context context){
        if(listeExercice ==null){
            listeExercice =new ListeExercice(context);
        }
        return listeExercice;
    }

    private ListeExercice(Context context){
        this.context=context.getApplicationContext();
        this.dataBase=new ExercicesDbHelper(context).getWritableDatabase();
    }

    public void ajouterExercice(Exercice exercice,UUID uuid){
        this.dataBase.insert(DbSchema.ExercicesTable.NAME,null,getContentValues(exercice,uuid));
    }

    public void supprimerExercice(Exercice exercice){
        this.dataBase.delete(DbSchema.ExercicesTable.NAME,DbSchema.ExercicesTable.cols.UUID+"=?",new String[]{
                String.valueOf(exercice.getId())
        });
    }

    public void updateExercice(Exercice exercice,UUID uuidSeance){
        UUID uuidExercice = exercice.getId();

        ContentValues values = getContentValues(exercice,uuidSeance);

        dataBase.update(DbSchema.ExercicesTable.NAME, values, DbSchema.ExercicesTable.cols.UUID + " = ?", new String[] {uuidExercice.toString()});
    }

    private ContentValues getContentValues(Exercice exercice,UUID uuid){
        ContentValues values = new ContentValues();
        values.put(DbSchema.ExercicesTable.cols.UUID,exercice.getId().toString());
        values.put(DbSchema.ExercicesTable.cols.NOM,exercice.getNom());
        values.put(DbSchema.ExercicesTable.cols.UUIDSEANCE,uuid.toString());
        return values;
    }

    private ExercicesCursorWrapper queryExercice(String whereClause, String[] whereArgs){
        return new ExercicesCursorWrapper(dataBase.query(DbSchema.ExercicesTable.NAME, null, whereClause,whereArgs,null,null,null));
    }

    public List<Exercice> getExercices(UUID uuid){
        ArrayList<Exercice> exercices= new ArrayList<>();

        ExercicesCursorWrapper cursor = queryExercice(DbSchema.ExercicesTable.cols.UUIDSEANCE +"= '"+uuid+"'",null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                exercices.add(cursor.getExercice());
                cursor.moveToNext();
            }
        }finally{
            cursor.close();
        }
        return exercices;
    }

    public Exercice getExercice(UUID id) {
        ExercicesCursorWrapper cursor = queryExercice(DbSchema.ExercicesTable.cols.UUID+ " = ? ", new String[] {id.toString()});
        try{
            if(cursor.getCount() == 0)
                return null;
            cursor.moveToFirst();
            return cursor.getExercice();
        }finally{
            cursor.close();
        }
    }

    public String getNomExercice(UUID uuid) {
        ExercicesCursorWrapper cursor=queryExercice(DbSchema.ExercicesTable.cols.UUID+"= '"+uuid+"'",null);
        try{
            if(cursor.getCount() == 0)
                return null;
            cursor.moveToFirst();
            return cursor.getNomExercice();
        }finally{
            cursor.close();
        }
    }
}
