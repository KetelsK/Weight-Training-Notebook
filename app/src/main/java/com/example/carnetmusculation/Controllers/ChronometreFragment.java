package com.example.carnetmusculation.Controllers;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;

import androidx.fragment.app.Fragment;

import com.example.carnetmusculation.R;

public class ChronometreFragment extends Fragment {

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean isRunning;
    private Button startButton;
    private Button pauseButton;
    private Button resetButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_chronometre, container, false);
        chronometer = view.findViewById(R.id.chrono);

        chronometer.setBase(SystemClock.elapsedRealtime());

        startButton = view.findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChronometer(v);
            }
        });
        pauseButton = view.findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseChronometer(v);
            }
        });
        resetButton = view.findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetChronometer(v);
            }
        });
        return view;
    }

    public void startChronometer(View view) {
        if (!isRunning) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            isRunning = true;
        }
    }

    public void pauseChronometer(View view) {
        if (isRunning) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            isRunning = false;
        }
    }

    public void resetChronometer(View view) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.stop();
        isRunning = false;
    }
}
