package com.example.carnetmusculation.Database.ExercicesDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.carnetmusculation.Database.DbSchema;

public class ExercicesDbHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DATABASE_NAME="exercices.db";

    public ExercicesDbHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
        //context.deleteDatabase(DATABASE_NAME);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ DbSchema.ExercicesTable.NAME + "("
                + "_id integer primary key autoincrement, "
                + DbSchema.ExercicesTable.cols.UUID + ", " + DbSchema.ExercicesTable.cols.NOM +", "+DbSchema.ExercicesTable.cols.UUIDSEANCE+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

