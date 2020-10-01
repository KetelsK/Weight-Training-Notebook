package com.example.carnetmusculation.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.carnetmusculation.Database.DbSchema;
import com.example.carnetmusculation.Database.SeancesDB.SeancesCursorWrapper;
import com.example.carnetmusculation.Database.SeancesDB.SeancesDbHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListeSeance implements Serializable {
    private Context context;
    private SQLiteDatabase dataBase;
    private static ListeSeance listeSeance;

    public static ListeSeance get(Context context){
        if(listeSeance ==null){
            listeSeance =new ListeSeance(context);
        }
        return listeSeance;
    }

    private ListeSeance(Context context){
        this.context=context.getApplicationContext();
        this.dataBase=new SeancesDbHelper(context).getWritableDatabase();
    }

    public void ajouterSeance(Seance seance, UUID uuid){
        this.dataBase.insert(DbSchema.SeancesTable.NAME,null,getContentValues(seance,uuid));
        //this.dataBase.insert(DbSchema.SeancesTable.NAME,null,getContentValues(seance));
    }

    public void supprimerSeance(Seance seance){
        this.dataBase.delete(DbSchema.SeancesTable.NAME,DbSchema.SeancesTable.cols.UUID +"=?",new String[]{
                String.valueOf(seance.getId())
        });
    }

    public void updateSeance(Seance seance,UUID uuidCarnet){
        UUID idSeance=seance.getId();

        ContentValues values = getContentValues(seance,uuidCarnet);

        dataBase.update(DbSchema.SeancesTable.NAME, values, DbSchema.SeancesTable.cols.UUID + " = ?", new String[] {String.valueOf(idSeance)});
    }

    private ContentValues getContentValues(Seance seance, UUID uuid){
        ContentValues values = new ContentValues();
        values.put(DbSchema.SeancesTable.cols.UUID,seance.getId().toString());
        values.put(DbSchema.SeancesTable.cols.NOM,seance.getNom());
        values.put(DbSchema.SeancesTable.cols.UUIDCARNET,uuid.toString());
        return values;
    }

    private SeancesCursorWrapper querySeance(String whereClause, String[] whereArgs){
        return new SeancesCursorWrapper(dataBase.query(DbSchema.SeancesTable.NAME, null, whereClause,whereArgs,null,null,null));
    }

    public List<Seance> getSeances(UUID uuid){
        ArrayList<Seance> seances= new ArrayList<>();

        SeancesCursorWrapper cursor = querySeance(DbSchema.SeancesTable.cols.UUIDCARNET +"= '"+uuid+"'",null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                seances.add(cursor.getSeance());
                cursor.moveToNext();
            }
        }finally{
            cursor.close();
        }
        return seances;
    }

    public Seance getSeance(UUID id) {
        SeancesCursorWrapper cursor = querySeance(DbSchema.SeancesTable.cols.UUID + " = ? ", new String[] {id.toString()});
        try{
            if(cursor.getCount() == 0)
                return null;
            cursor.moveToFirst();
            return cursor.getSeance();
        }finally{
            cursor.close();
        }
    }

    public String getNomSeance(UUID uuid) {
        SeancesCursorWrapper cursor=querySeance(DbSchema.SeancesTable.cols.UUID+"= '"+uuid+"'",null);
        try{
            if(cursor.getCount() == 0)
                return null;
            cursor.moveToFirst();
            return cursor.getNomSeance();
        }finally{
            cursor.close();
        }
    }

}

