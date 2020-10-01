package com.example.carnetmusculation.Database.CarnetDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.carnetmusculation.Database.DbSchema;

public class CarnetDbHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DATABASE_NAME="carnetBase.db";

    public CarnetDbHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
        //context.deleteDatabase(DATABASE_NAME);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ DbSchema.CarnetsTable.NAME + "("
                + "_id integer primary key autoincrement, "
                + DbSchema.CarnetsTable.cols.UUID + ", " + DbSchema.CarnetsTable.cols.NOM +", "+DbSchema.CarnetsTable.cols.UUIDSeance+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
