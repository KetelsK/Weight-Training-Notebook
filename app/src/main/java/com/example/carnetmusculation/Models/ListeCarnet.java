package com.example.carnetmusculation.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.carnetmusculation.Database.CarnetDB.CarnetCursorWrapper;
import com.example.carnetmusculation.Database.CarnetDB.CarnetDbHelper;
import com.example.carnetmusculation.Database.DbSchema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListeCarnet implements Serializable {

    private Context context;
    private SQLiteDatabase dataBase;
    private static ListeCarnet listeCarnet;

    public static ListeCarnet get(Context context){
        if(listeCarnet==null){
            listeCarnet=new ListeCarnet(context);
        }
        return listeCarnet;
    }

    private ListeCarnet(Context context){
        this.context=context.getApplicationContext();
        this.dataBase=new CarnetDbHelper(context).getWritableDatabase();
    }

    public void ajouterCarnet(Carnet carnet){
        this.dataBase.insert(DbSchema.CarnetsTable.NAME,null,getContentValues(carnet));
    }

    public void supprimerCarnet(Carnet carnet){
        this.dataBase.delete(DbSchema.CarnetsTable.NAME,DbSchema.CarnetsTable.cols.UUID +"=?",new String[]{
                String.valueOf(carnet.getId())
        });
    }

    public void updateCarnet(Carnet carnet){
        UUID idCarnet = carnet.getId();

        ContentValues values = getContentValues(carnet);

        dataBase.update(DbSchema.CarnetsTable.NAME, values, DbSchema.CarnetsTable.cols.UUID + " = ?", new String[]{idCarnet.toString()});
    }

    private ContentValues getContentValues(Carnet carnet){
        ContentValues values = new ContentValues();
        values.put(DbSchema.CarnetsTable.cols.UUID,carnet.getId().toString());
        values.put(DbSchema.CarnetsTable.cols.NOM,carnet.getNom());
        return values;
    }

    private CarnetCursorWrapper queryCarnet(String whereClause,String[] whereArgs){
        return new CarnetCursorWrapper(dataBase.query(DbSchema.CarnetsTable.NAME, null, whereClause,whereArgs,null,null,null));
    }

    public List<Carnet> getCarnets(){
        ArrayList<Carnet> carnets= new ArrayList<>();

        CarnetCursorWrapper cursor = queryCarnet(null, null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                carnets.add(cursor.getCarnet());
                cursor.moveToNext();
            }
        }finally{
            cursor.close();
        }
        return carnets;
    }

    public String getNomCarnet(UUID uuid){
        CarnetCursorWrapper cursor=queryCarnet(DbSchema.CarnetsTable.cols.UUID+"= '"+uuid+"'",null);
        try{
            if(cursor.getCount() == 0)
                return null;
            cursor.moveToFirst();
            return cursor.getNomCarnet();
        }finally{
            cursor.close();
        }
    }

    public Carnet getCarnet(UUID id) {
        CarnetCursorWrapper cursor = queryCarnet(DbSchema.CarnetsTable.cols.UUID + " = ? ", new String[] {id.toString()});
        try{
            if(cursor.getCount() == 0)
                return null;
            cursor.moveToFirst();
            return cursor.getCarnet();
        }finally{
            cursor.close();
        }
    }
}

