package com.example.carnetmusculation.Controllers;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.carnetmusculation.Models.Exercice;
import com.example.carnetmusculation.Models.ListeMuscle;
import com.example.carnetmusculation.Models.MesExercices;
import com.example.carnetmusculation.Models.Muscle;
import com.example.carnetmusculation.R;

public class MesExercicesFragment extends Fragment {
    private ImageView addButton;
    private LinearLayout linearLayout;
    private SearchView searchView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_mes_exercices,container,false);
        getActivity().setTitle("Mes exercices");
        linearLayout=(LinearLayout)view.findViewById(R.id.linearLayoutExercices);

        searchView=view.findViewById(R.id.searchViewExercices);
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.setQueryHint("Rechercher un exercice");
        searchView.setBackgroundColor(Color.WHITE);

        addButton=view.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*NewExerciceActivity fragment=new NewExerciceActivity();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,fragment,"fragment")
                        .addToBackStack(null).commit();*/
            }
        });
        updateUI();
        return view;
    }

    private void updateUI() {
        ListeMuscle listeMuscle= ListeMuscle.get(getActivity());
        listeMuscle.initMuscles();
        linearLayout.removeAllViews();
        for(Muscle muscle:listeMuscle.getListeMuscles()){
            linearLayout.addView(textView(muscle));
            for(Exercice exercice:muscle.getListeExercices()){
                linearLayout.addView(editText(exercice));
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }
    private TextView textView(Muscle muscle){
        TextView textView=new TextView(getContext());
        textView.setText(muscle.getNom());
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(16);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setBackgroundResource(R.color.colorPrimary);
        return textView;
    }

    private EditText editText(Exercice exercice){
        EditText editText=new EditText(getContext());
        editText.setFocusable(false);
        editText.setCursorVisible(false);
        editText.setText(exercice.getNom());
        editText.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.lightgrey));
        editText.setHeight(150);
        editText.setWidth(linearLayout.getWidth());
        return editText;
    }

    private void popupConfirmation(final MesExercices mesExercices, final Exercice exercice) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setCancelable(true);
        alert.setTitle("Confirmation suppression");
        alert.setMessage("Voulez-vous supprimer cet exercice ?");
        alert.setPositiveButton("Confirmer",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mesExercices.supprimerExercice(exercice);
                        updateUI();
                    }
                });
        alert.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //DO NOTHING
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

}

