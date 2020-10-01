package com.example.carnetmusculation.Controllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carnetmusculation.Models.ListeExercice;
import com.example.carnetmusculation.Models.ListePerformance;
import com.example.carnetmusculation.Models.Performance;
import com.example.carnetmusculation.R;

import java.util.UUID;

public class PerformanceActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private ImageView addButton;
    private UUID uuidExercice;
    private TextView TVNomExercice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);
        setTitle("Séries");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //on récupère l'uuid du carnet
        uuidExercice =(UUID) getIntent().getSerializableExtra("idExercice");

        linearLayout=(LinearLayout)findViewById(R.id.linearLayoutPerfomance);

        addButton=findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),NewPerformanceActivity.class);
                intent.putExtra("idExercice", uuidExercice);
                startActivity(intent);
            }
        });
        ListeExercice listeExercice=ListeExercice.get(this);
        TVNomExercice =findViewById(R.id.nomExercice);
        TVNomExercice.setText(listeExercice.getNomExercice(uuidExercice));
        updateUI();
    }

    public void updateUI() {
        final ListePerformance listePerformance = ListePerformance.get(this);
        linearLayout.removeAllViews();
        for (final Performance performance : listePerformance.getPerformances(uuidExercice)) {
            String date=performance.getDate();
            int nbReps=performance.getNbReps();
            int poids=performance.getPoids();
            //linearLayout.addView(linearLayoutDate(date,nbReps,poids));
            linearLayout.addView(editTextPerf(nbReps,poids,date,listePerformance,performance));
        }
    }

    private LinearLayout linearLayoutDate(String date, int nbReps, int poids) {
        LinearLayout linearLayoutDate=new LinearLayout(this);
        linearLayoutDate.setOrientation(LinearLayout.HORIZONTAL);
        TextView TVDate=new TextView(this);
        TVDate.setText(date+"   (Total: "+nbReps+" REPS, "+poids+" KG)");
        TVDate.setTextSize(18);
        TVDate.setTextColor(Color.WHITE);
        linearLayoutDate.addView(TVDate);
        linearLayoutDate.setBackgroundResource(R.color.colorPrimary);
        return linearLayoutDate;
    }

    private EditText editTextPerf(int nbReps, int poids, String date, final ListePerformance listePerformance, final Performance performance) {
        final EditText editText=new EditText(this);
        editText.setFocusable(false);
        editText.setCursorVisible(false);
        editText.setBackgroundTintList(getResources().getColorStateList(R.color.lightgrey));
        editText.setHeight(200);
        editText.setWidth(linearLayout.getWidth());
        editText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                editText.setBackgroundResource(R.color.colorPrimary);
                popupConfirmation(listePerformance,performance);
                return true;
            }
        });
        editText.setText("   "+nbReps+" répétitions à "+poids+" KG ("+date+")");
        return editText;
    }

    private void popupConfirmation(final ListePerformance listePerformance, final Performance performance) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable(true);
        alert.setTitle("Choix action");
        alert.setMessage("Que voulez-vous faire ?");
        alert.setPositiveButton("Supprimer",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listePerformance.supprimerPerformance(performance);
                        updateUI();
                    }
                });
        alert.setNegativeButton("Modifier", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), NewPerformanceActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable("modification", performance);
                extras.putSerializable("idExercice", uuidExercice);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

