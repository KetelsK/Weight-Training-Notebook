package com.example.carnetmusculation.Controllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carnetmusculation.Models.Exercice;
import com.example.carnetmusculation.Models.ListeExercice;
import com.example.carnetmusculation.Models.ListeSeance;
import com.example.carnetmusculation.R;

import java.util.UUID;

public class ExercicesActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private ImageView addButton;
    private UUID uuidSeance;
    private TextView TVNomSeance;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercices);
        setTitle("Exercices");
        //pour afficher le bouton retour dans la toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //on récupère l'uuid de la séance
        uuidSeance =(UUID) getIntent().getSerializableExtra("idSeance");

        linearLayout=(LinearLayout)findViewById(R.id.linearLayoutExercices);

        addButton=findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), NewExerciceActivity.class);
                intent.putExtra("idSeance", uuidSeance);
                startActivity(intent);
            }
        });
        ListeSeance listeSeance=ListeSeance.get(this);
        TVNomSeance=findViewById(R.id.nomSeance);
        TVNomSeance.setText(listeSeance.getNomSeance(uuidSeance));
        updateUI();
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    public void updateUI() {
        final ListeExercice listeExercice = ListeExercice.get(this);
        linearLayout.removeAllViews();
        for (final Exercice exercices : listeExercice.getExercices(uuidSeance)) {

            linearLayout.addView(editText(listeExercice,exercices));
        }
    }

    private EditText editText(final ListeExercice listeExercice, final Exercice exercice){
        final EditText editText=new EditText(this);
        editText.setFocusable(false);
        editText.setBackgroundTintList(getResources().getColorStateList(R.color.lightgrey));
        editText.setHeight(200);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),PerformanceActivity.class);
                intent.putExtra("idExercice",exercice.getId());
                startActivity(intent);
                editText.setBackgroundResource(R.color.colorPrimary);
            }
        });
        editText.setWidth(linearLayout.getWidth());
        editText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                editText.setBackgroundResource(R.color.colorPrimary);
                popupConfirmation(listeExercice,exercice);
                return true;
            }
        });
        editText.setText(exercice.getNom());
        return editText;
    }

    private void popupConfirmation(final ListeExercice listeExercice, final Exercice exercice) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable(true);
        alert.setTitle("Choix action");
        alert.setMessage("Que voulez-vous faire ?");
        alert.setPositiveButton("Supprimer",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listeExercice.supprimerExercice(exercice);
                        updateUI();
                    }
                });
        alert.setNegativeButton("Modifier", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(getApplicationContext(),NewExerciceActivity.class);
                Bundle extras=new Bundle();
                extras.putSerializable("modification",exercice);
                extras.putSerializable("idSeance",uuidSeance);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
