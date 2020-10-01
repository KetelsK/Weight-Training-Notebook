package com.example.carnetmusculation.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.carnetmusculation.Database.DbSchema;
import com.example.carnetmusculation.Database.MesExercicesDB.MesExercicesCursorWrapper;
import com.example.carnetmusculation.Database.MesExercicesDB.MesExercicesDbHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MesExercices {
    private Context context;
    private SQLiteDatabase dataBase;
    private static MesExercices listeExercice;

    public static MesExercices get(Context context){
        if(listeExercice ==null){
            listeExercice =new MesExercices(context);
        }
        return listeExercice;
    }

    private MesExercices(Context context){
        this.context=context.getApplicationContext();
        this.dataBase=new MesExercicesDbHelper(context).getWritableDatabase();
    }

    public void ajouterExercice(Exercice exercice){
        this.dataBase.insert(DbSchema.MesExercicesTable.NAME,null,getContentValues(exercice));
    }

    public void supprimerExercice(Exercice exercice){
        this.dataBase.delete(DbSchema.MesExercicesTable.NAME,DbSchema.MesExercicesTable.cols.UUID+"=?",new String[]{
                String.valueOf(exercice.getId())
        });
    }

    public void updateExercice(Exercice exercice){
        String uuidString = exercice.getId().toString();

        ContentValues values = getContentValues(exercice);

        dataBase.update(DbSchema.MesExercicesTable.NAME, values, DbSchema.MesExercicesTable.cols.UUID + " = ?", new String[] {uuidString});
    }

    private ContentValues getContentValues(Exercice exercice){
        ContentValues values = new ContentValues();
        values.put(DbSchema.MesExercicesTable.cols.UUID,exercice.getId().toString());
        values.put(DbSchema.MesExercicesTable.cols.NOM,exercice.getNom());
        return values;
    }

    private MesExercicesCursorWrapper queryExercice(String whereClause,String[] whereArgs){
        return new MesExercicesCursorWrapper(dataBase.query(DbSchema.MesExercicesTable.NAME, null, whereClause,whereArgs,null,null,null));
    }

    public List<Exercice> getExercices(){
        ArrayList<Exercice> exercices= new ArrayList<>();

        MesExercicesCursorWrapper cursor = queryExercice(null, null);
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
        MesExercicesCursorWrapper cursor = queryExercice(DbSchema.MesExercicesTable.cols.UUID+ " = ? ", new String[] {id.toString()});
        try{
            if(cursor.getCount() == 0)
                return null;
            cursor.moveToFirst();
            return cursor.getExercice();
        }finally{
            cursor.close();
        }
    }
}

