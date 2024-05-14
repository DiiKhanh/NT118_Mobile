package com.example.lab4_bt2;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private PowerStateChangeReceiver powerStateChangeReceiver;

    private void initValue() {
        powerStateChangeReceiver = new PowerStateChangeReceiver();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initValue();

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);

        registerReceiver(
                powerStateChangeReceiver,
                intentFilter
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(powerStateChangeReceiver);
    }
}