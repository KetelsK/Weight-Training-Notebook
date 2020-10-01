package com.example.carnetmusculation.Database.SeancesDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.carnetmusculation.Database.DbSchema;

public class SeancesDbHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DATABASE_NAME="seancesBase.db";

    public SeancesDbHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
        //context.deleteDatabase(DATABASE_NAME);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ DbSchema.SeancesTable.NAME + "("
                + "_id integer primary key autoincrement, "
                + DbSchema.SeancesTable.cols.UUID + ", " + DbSchema.SeancesTable.cols.NOM +", "+DbSchema.SeancesTable.cols.UUIDCARNET +")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

