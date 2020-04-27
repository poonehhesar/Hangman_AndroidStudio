package com.example.hangman2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class saveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("lifecycle", "onStart invoked " + getApplication());
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifecycle", "onResume invoked " + getApplication());
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifecycle", "onPause invoked " + getApplication());
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifecycle", "onStart invoked " + getApplication());
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("lifecycle", "onRestart invoked "+getApplication());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifecycle", "onDestroy invoked " + getApplication());
    }

}
