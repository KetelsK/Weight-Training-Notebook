package com.example.carnetmusculation.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carnetmusculation.Models.Exercice;
import com.example.carnetmusculation.Models.ListeExercice;
import com.example.carnetmusculation.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.UUID;

public class NewExerciceActivity extends AppCompatActivity {
    private TextInputEditText textInput;
    private UUID uuidSeance;
    private Button saveButton;
    private Exercice exercice;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercice);
        setTitle("Exercices");

        //pour afficher le bouton retour dans la toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        saveButton=findViewById(R.id.saveButton);
        textInput=findViewById(R.id.textInput);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        //on récupère l'uuid du carnet et la séance
        uuidSeance= (UUID) extras.getSerializable("idSeance");
        exercice= (Exercice) extras.getSerializable("modification");

        if(exercice!=null){
            textInput.setText(exercice.getNom());
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!textInput.getText().toString().matches("")){
                    //modification exercice
                    if(exercice!=null){
                        ListeExercice listeExercice=ListeExercice.get(v.getContext());
                        exercice.setNom(textInput.getText().toString());
                        listeExercice.updateExercice(exercice,uuidSeance);
                    }
                    //ajout exercice
                    else{
                        Exercice exercice=new Exercice();
                        exercice.setNom(textInput.getText().toString());
                        ListeExercice listeExercice=ListeExercice.get(v.getContext());
                        listeExercice.ajouterExercice(exercice, uuidSeance);
                    }
                    //finish pour retourner a ExercicesActivity
                    finish();
                }
                else{
                    Toast toast=Toast.makeText(v.getContext(),"Le nom ne doit pas être vide",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

