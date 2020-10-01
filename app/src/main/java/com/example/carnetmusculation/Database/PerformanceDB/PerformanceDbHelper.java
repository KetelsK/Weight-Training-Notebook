package com.example.carnetmusculation.Database.PerformanceDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.carnetmusculation.Database.DbSchema;

public class PerformanceDbHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DATABASE_NAME="performance.db";

    public PerformanceDbHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ DbSchema.PerformanceTable.NAME+"("
                +"_id integer primary key autoincrement, "
                +DbSchema.PerformanceTable.cols.UUID+", "
                +DbSchema.PerformanceTable.cols.DATE+", "
                +DbSchema.PerformanceTable.cols.POIDS+", "
                +DbSchema.PerformanceTable.cols.NBREPS+", "
                +DbSchema.PerformanceTable.cols.UUIDEXERCICE+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

