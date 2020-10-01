package com.example.carnetmusculation.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.carnetmusculation.Database.DbSchema;
import com.example.carnetmusculation.Database.MuscleDB.MuscleCursorWrapper;
import com.example.carnetmusculation.Database.MuscleDB.MuscleDbHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListeMuscle implements Serializable {
    private Context context;
    private SQLiteDatabase dataBase;
    private static ListeMuscle listeMuscle;
    private ArrayList<Muscle>listeMuscles;
    public static ListeMuscle get(Context context){
        if(listeMuscle ==null){
            listeMuscle =new ListeMuscle(context);
        }
        return listeMuscle;
    }

    public ArrayList<Muscle> getListeMuscles() {
        return listeMuscles;
    }

    public void initMuscles(){
        listeMuscles=new ArrayList<>();
        Muscle biceps=new Muscle("Biceps");
        biceps.ajouterExercice(new Exercice("Curl"));
        biceps.ajouterExercice(new Exercice("Curl marteau"));
        biceps.ajouterExercice(new Exercice("Curl haltères"));
        biceps.ajouterExercice(new Exercice("Curl barre"));
        biceps.ajouterExercice(new Exercice("Curl poulie"));
        Muscle pectoraux=new Muscle("Pectoraux");
        pectoraux.ajouterExercice(new Exercice("Développé couché"));
        pectoraux.ajouterExercice(new Exercice("Développé incliné"));
        pectoraux.ajouterExercice(new Exercice("Développé décliné"));
        pectoraux.ajouterExercice(new Exercice("Ecartés poulie"));
        pectoraux.ajouterExercice(new Exercice("Pec deck"));
        Muscle dos=new Muscle("Dos");
        dos.ajouterExercice(new Exercice("Tractions pronation"));
        dos.ajouterExercice(new Exercice("Tractions supination"));
        dos.ajouterExercice(new Exercice("Tirage horizontal"));
        dos.ajouterExercice(new Exercice("Tirage vertical"));
        dos.ajouterExercice(new Exercice("Tirage vertical prise serrée"));
        Muscle triceps=new Muscle("Triceps");
        triceps.ajouterExercice(new Exercice("Barre au front"));
        triceps.ajouterExercice(new Exercice("Extension haltères"));
        triceps.ajouterExercice(new Exercice("Extension corde"));
        triceps.ajouterExercice(new Exercice("Dips"));
        triceps.ajouterExercice(new Exercice("Extension poulie"));
        Muscle epaule=new Muscle("Epaules");
        epaule.ajouterExercice(new Exercice("Développé militaire barre"));
        epaule.ajouterExercice(new Exercice("Développé militaire haltères"));
        epaule.ajouterExercice(new Exercice("Elévations latérales"));
        epaule.ajouterExercice(new Exercice("Elévations frontales"));
        epaule.ajouterExercice(new Exercice("Oiseau avec haltères"));
        Muscle abdominaux=new Muscle("Abdominaux");
        abdominaux.ajouterExercice(new Exercice("Crunch"));
        abdominaux.ajouterExercice(new Exercice("Crunch au banc incliné"));
        abdominaux.ajouterExercice(new Exercice("Crunch poulie"));
        abdominaux.ajouterExercice(new Exercice("Obliques"));
        abdominaux.ajouterExercice(new Exercice("Relevé de jambes"));
        Muscle jambes=new Muscle("Jambes");
        jambes.ajouterExercice(new Exercice("Squat"));
        jambes.ajouterExercice(new Exercice("Presse à cuisse"));
        jambes.ajouterExercice(new Exercice("Leg curl"));
        jambes.ajouterExercice(new Exercice("Fentes avec haltères"));
        jambes.ajouterExercice(new Exercice("Leg extension"));
        listeMuscles.add(biceps);
        listeMuscles.add(pectoraux);
        listeMuscles.add(dos);
        listeMuscles.add(triceps);
        listeMuscles.add(epaule);
        listeMuscles.add(abdominaux);
        listeMuscles.add(jambes);
    }

    private ListeMuscle(Context context){
        this.context=context.getApplicationContext();
        this.dataBase=new MuscleDbHelper(context).getWritableDatabase();
    }

    public void ajouterMuscle(Muscle muscle){
        this.dataBase.insert(DbSchema.MuscleTable.NAME,null,getContentValues(muscle));
    }

    public void supprimerMuscle(Muscle muscle){
        this.dataBase.delete(DbSchema.MuscleTable.NAME,DbSchema.MuscleTable.cols.UUID+"=?",new String[]{
                String.valueOf(muscle.getId())
        });
    }

    public void updateMuscle(Muscle muscle){
        String uuidString = muscle.getId().toString();

        ContentValues values = getContentValues(muscle);

        dataBase.update(DbSchema.MuscleTable.NAME, values, DbSchema.MuscleTable.cols.UUID + " = ?", new String[] {uuidString});
    }

    private ContentValues getContentValues(Muscle muscle){
        ContentValues values = new ContentValues();
        values.put(DbSchema.MuscleTable.cols.UUID,muscle.getId().toString());
        values.put(DbSchema.MuscleTable.cols.NOM,muscle.getNom());
        return values;
    }

    private MuscleCursorWrapper queryMuscle(String whereClause,String[] whereArgs){
        return new MuscleCursorWrapper(dataBase.query(DbSchema.MuscleTable.NAME, null, whereClause,whereArgs,null,null,null));
    }

    public List<Muscle> getMuscles(){
        ArrayList<Muscle> muscles= new ArrayList<>();

        MuscleCursorWrapper cursor = queryMuscle(null, null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                muscles.add(cursor.getMuscle());
                cursor.moveToNext();
            }
        }finally{
            cursor.close();
        }
        return muscles;
    }

    public Muscle getMuscle(UUID id) {
        MuscleCursorWrapper cursor = queryMuscle(DbSchema.MuscleTable.cols.UUID+ " = ? ", new String[] {id.toString()});
        try{
            if(cursor.getCount() == 0)
                return null;
            cursor.moveToFirst();
            return cursor.getMuscle();
        }finally{
            cursor.close();
        }
    }
}

