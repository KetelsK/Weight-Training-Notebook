package com.example.carnetmusculation.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carnetmusculation.Models.ListePerformance;
import com.example.carnetmusculation.Models.Performance;
import com.example.carnetmusculation.R;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class NewPerformanceActivity extends AppCompatActivity {
    private TextInputEditText textInputNbReps;
    private TextInputEditText textInputPoids;
    private UUID uuidExercice;
    private Button saveButton;
    private Performance performance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_performance);
        setTitle("Séries");

        //pour afficher le bouton retour dans la toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        saveButton = findViewById(R.id.saveButton);
        textInputNbReps = findViewById(R.id.textInputNbReps);
        textInputPoids = findViewById(R.id.textInputPoids);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        //on récupère l'uuid de l'exercice et la performance
        uuidExercice= (UUID) extras.getSerializable("idExercice");
        performance= (Performance) extras.getSerializable("modification");

        if(performance!=null){
            textInputNbReps.setText(performance.getNbReps()+"");
            textInputPoids.setText(performance.getPoids()+"");
        }
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textInputNbReps.getText().toString().matches("") || !textInputPoids.getText().toString().matches("")) {

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    //modification performance
                    if(performance!=null){
                        ListePerformance listeperformance = ListePerformance.get(v.getContext());
                        performance.setNbReps(Integer.parseInt(textInputNbReps.getText().toString()));
                        performance.setPoids(Integer.parseInt(textInputPoids.getText().toString()));
                        listeperformance.updatePerformance(performance,uuidExercice);
                    }
                    //ajout performance
                    else{
                        Performance performance = new Performance();
                        performance.setDate(sdf.format(new Date()));
                        performance.setPoids(Integer.parseInt(textInputPoids.getText().toString()));
                        performance.setNbReps(Integer.parseInt(textInputNbReps.getText().toString()));

                        ListePerformance listeperformance = ListePerformance.get(v.getContext());
                        listeperformance.ajouterPerformance(performance, uuidExercice);
                    }

                    finish();
                } else {
                    Toast toast = Toast.makeText(v.getContext(), "Les champs ne doivent pas être vides.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}

