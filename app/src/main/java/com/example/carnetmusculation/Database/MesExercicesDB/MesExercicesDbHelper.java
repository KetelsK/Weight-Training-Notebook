package com.example.carnetmusculation.Database.MesExercicesDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.carnetmusculation.Database.DbSchema;

public class MesExercicesDbHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DATABASE_NAME="mesExercices.db";

    public MesExercicesDbHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
        //context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ DbSchema.MesExercicesTable.NAME + "("
                + "_id integer primary key autoincrement, "
                + DbSchema.MesExercicesTable.cols.UUID + ", " + DbSchema.MesExercicesTable.cols.NOM +")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

