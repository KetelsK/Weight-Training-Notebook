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

import com.example.carnetmusculation.Models.ListeCarnet;
import com.example.carnetmusculation.Models.ListeSeance;
import com.example.carnetmusculation.Models.Seance;
import com.example.carnetmusculation.R;

import java.util.UUID;

public class SeancesActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private ImageView addButton;
    private UUID uuidCarnet;
    private TextView TVNomCarnet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seances);
        setTitle("Mes séances");

        //pour afficher le bouton retour dans la toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //on récupère l'uuid du carnet
        uuidCarnet = (UUID) getIntent().getSerializableExtra("idCarnet");

        linearLayout = (LinearLayout) findViewById(R.id.linearLayoutSeances);

        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NewSeanceActivity.class);
                intent.putExtra("idCarnet", uuidCarnet);
                startActivity(intent);
            }
        });
        ListeCarnet listeCarnet = ListeCarnet.get(this);
        TVNomCarnet = findViewById(R.id.nomCarnet);
        TVNomCarnet.setText(listeCarnet.getNomCarnet(uuidCarnet));
        updateUI();
    }


    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI() {
        final ListeSeance listeSeance = ListeSeance.get(this);
        linearLayout.removeAllViews();
        for (final Seance seances : listeSeance.getSeances(uuidCarnet)) {
            linearLayout.addView(editText(listeSeance, seances));
        }
    }

    private EditText editText(final ListeSeance listeSeance, final Seance seance) {
        final EditText editText = new EditText(this);
        editText.setFocusable(false);
        editText.setBackgroundTintList(getResources().getColorStateList(R.color.lightgrey));
        editText.setHeight(200);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on envoie l'uuid du carnet et de la séance
                Intent intent = new Intent(v.getContext(), ExercicesActivity.class);
                intent.putExtra("idSeance", seance.getId());
                startActivity(intent);
                editText.setBackgroundResource(R.color.colorPrimary);
            }
        });
        editText.setWidth(linearLayout.getWidth());
        editText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                editText.setBackgroundResource(R.color.colorPrimary);
                popupConfirmation(listeSeance, seance);
                return true;
            }
        });
        editText.setText(seance.getNom());
        return editText;
    }

    private void popupConfirmation(final ListeSeance listeSeance, final Seance seance) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable(true);
        alert.setTitle("Choix action");
        alert.setMessage("Que voulez-vous faire ?");
        alert.setPositiveButton("Supprimer",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listeSeance.supprimerSeance(seance);
                        updateUI();
                    }
                });
        alert.setNegativeButton("Modifier", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), NewSeanceActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable("modification", seance);
                extras.putSerializable("idCarnet", uuidCarnet);
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
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
