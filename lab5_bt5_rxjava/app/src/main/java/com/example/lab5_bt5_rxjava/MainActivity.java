package com.example.lab5_bt5_rxjava;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;




public class MainActivity extends AppCompatActivity {

    private Button startButton, pauseButton, showAuthorButton;
    private ProgressBar progressBar;
    private TextView textView;
    private MediaPlayer mediaPlayer;

    private boolean isPlaying = false, isStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
//        showAuthorButton = findViewById(R.id.showAuthorButton);
        progressBar = findViewById(R.id.progressBar);

        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        progressBar.setMax(mediaPlayer.getDuration() / 1000);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStarted = true;
                isPlaying = true;
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
                StartTask();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null && isStarted) {
                    if (isPlaying) {
                        mediaPlayer.pause();
                        isPlaying = false;
                    } else {
                        mediaPlayer.start();
                        isPlaying = true;
                    }
                }
            }
        });

//        showAuthorButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                textView.setText("Elements Garden");
//            }
//        });
    }

    private void StartTask() {

        Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        count -> {
                            if (isStarted && isPlaying) {
                                int currentSecond = mediaPlayer.getCurrentPosition() / 1000;
                                progressBar.setProgress(currentSecond);
                                runOnUiThread(() -> textView.setText(formatDuration(currentSecond)));

                                if (currentSecond >= progressBar.getMax()) {
                                    mediaPlayer.stop();
                                    isStarted = false;
                                    isPlaying = false;
                                }
                            }
                        },
                        Throwable::printStackTrace
                );
    }

    private String formatDuration(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}

