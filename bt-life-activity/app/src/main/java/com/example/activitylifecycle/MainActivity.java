package com.example.activitylifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String STATE = "Trang thai";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e(STATE, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(STATE, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(STATE, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e(STATE, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.e(STATE, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.e(STATE, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.e(STATE, "onDestroy");
    }

}