package com.example.carnetmusculation.Controllers;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carnetmusculation.Models.Carnet;
import com.example.carnetmusculation.Models.ListeCarnet;
import com.example.carnetmusculation.R;
import com.google.android.material.textfield.TextInputEditText;


public class NewCarnetActivity extends AppCompatActivity {
    private Button saveButton;
    private TextInputEditText textInput;
    private Carnet carnet;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_carnet);
        setTitle("Mes carnets");

        saveButton=findViewById(R.id.saveButton);
        textInput=findViewById(R.id.textInput);

        carnet= (Carnet) getIntent().getSerializableExtra("modification");
        if(carnet!=null){
            textInput.setText(carnet.getNom());
        }
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!textInput.getText().toString().matches("")){
                    //modification carnet
                    if(carnet!=null){
                        ListeCarnet listeCarnet=ListeCarnet.get(v.getContext());
                        carnet.setNom(textInput.getText().toString());
                        listeCarnet.updateCarnet(carnet);
                    }
                    //ajout carnet
                    else{
                        Carnet carnet=new Carnet();
                        carnet.setNom(textInput.getText().toString());
                        ListeCarnet listeCarnet=ListeCarnet.get(v.getContext());
                        listeCarnet.ajouterCarnet(carnet);
                    }
                    finish();
                    hideKeyboard();
                }
                else{
                    Toast toast=Toast.makeText(v.getContext(),"Le nom ne doit pas être vide",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    private void hideKeyboard() {
        View view = findViewById(R.id.textInput);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}

