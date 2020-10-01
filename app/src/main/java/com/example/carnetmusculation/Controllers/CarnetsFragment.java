package com.example.carnetmusculation.Controllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.carnetmusculation.Models.Carnet;
import com.example.carnetmusculation.Models.ListeCarnet;
import com.example.carnetmusculation.R;

public class CarnetsFragment extends Fragment {
    private LinearLayout linearLayout;
    private ImageView addButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_carnets,container,false);
        getActivity().setTitle("Mes carnets");

        linearLayout=(LinearLayout)view.findViewById(R.id.linearLayoutCarnets);
        addButton=view.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),NewCarnetActivity.class);
                startActivity(intent);
            }
        });
        updateUI();
        return view;
    }


    public void updateUI() {
        final ListeCarnet listeCarnet = ListeCarnet.get(getActivity());
        linearLayout.removeAllViews();
        for (final Carnet carnets : listeCarnet.getCarnets()) {
            linearLayout.addView(editText(listeCarnet,carnets));
        }
    }

    private EditText editText(final ListeCarnet listeCarnet, final Carnet carnet){
        final EditText editText=new EditText(getContext());
        editText.setFocusable(false);
        editText.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.lightgrey));
        editText.setHeight(200);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),SeancesActivity.class);
                //on envoie l'uuid du carnet
                intent.putExtra("idCarnet",carnet.getId());
                startActivity(intent);
                editText.setBackgroundResource(R.color.colorPrimary);
            }
        });
        editText.setWidth(linearLayout.getWidth());
        editText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                editText.setBackgroundResource(R.color.colorPrimary);
                popupConfirmation(listeCarnet,carnet);
                return true;
            }
        });
        editText.setText(carnet.getNom());
        return editText;
    }

    private void popupConfirmation(final ListeCarnet listeCarnet, final Carnet carnet) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setCancelable(true);
        alert.setTitle("Choix action");
        alert.setMessage("Que voulez-vous faire ?");
        alert.setPositiveButton("Supprimer",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listeCarnet.supprimerCarnet(carnet);
                        updateUI();
                    }
                });
        alert.setNegativeButton("Modifier", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(getContext(),NewCarnetActivity.class);
                intent.putExtra("modification",carnet);
                startActivity(intent);
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });
    }
}
