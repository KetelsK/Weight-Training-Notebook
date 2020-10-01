package com.example.carnetmusculation.Database.MuscleDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.carnetmusculation.Database.DbSchema;

public class MuscleDbHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DATABASE_NAME="muscle.db";

    public MuscleDbHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
        context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ DbSchema.MuscleTable.NAME + "("
                + "_id integer primary key autoincrement, "
                + DbSchema.MuscleTable.cols.UUID + ", " + DbSchema.MuscleTable.cols.NOM +")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

