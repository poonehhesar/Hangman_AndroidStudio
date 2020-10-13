package com.example.hangman2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    private static final String TAG = "score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Bundle extras = getIntent().getExtras();
        String wins = String.valueOf(extras.getInt("wins"));
        Log.d(TAG, "onCreate: "+ extras.getInt("wins"));
       TextView txtScore = findViewById(R.id.txtScore);
        txtScore.setText(wins);

    }
}
