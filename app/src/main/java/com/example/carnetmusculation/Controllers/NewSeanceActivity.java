package com.example.carnetmusculation.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carnetmusculation.Models.ListeSeance;
import com.example.carnetmusculation.Models.Seance;
import com.example.carnetmusculation.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.UUID;

public class NewSeanceActivity extends AppCompatActivity {
    private TextInputEditText textInput;
    private UUID uuidCarnet;
    private Button saveButton;
    private Seance seance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_seance);
        setTitle("Mes séances");

        //pour afficher le bouton retour dans la toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        saveButton = findViewById(R.id.saveButton);
        textInput = findViewById(R.id.textInput);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        //on récupère l'uuid du carnet et la séance
        uuidCarnet = (UUID) extras.getSerializable("idCarnet");
        seance = (Seance) extras.getSerializable("modification");

        if (seance != null) {
            textInput.setText(seance.getNom());
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textInput.getText().toString().matches("")) {
                    //modification séance
                    if (seance != null) {
                        ListeSeance listeSeance = ListeSeance.get(v.getContext());
                        seance.setNom(textInput.getText().toString());
                        listeSeance.updateSeance(seance, uuidCarnet);
                    }
                    //ajout séance
                    else {
                        Seance seance = new Seance();
                        seance.setNom(textInput.getText().toString());
                        ListeSeance listeSeance = ListeSeance.get(v.getContext());
                        listeSeance.ajouterSeance(seance, uuidCarnet);
                    }
                    finish();
                } else {
                    Toast toast = Toast.makeText(v.getContext(), "Le nom ne doit pas être vide", Toast.LENGTH_SHORT);
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
