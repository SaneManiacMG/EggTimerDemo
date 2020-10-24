package com.sanemaniac.eggtimerdemo;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    SeekBar sbTimer;
    TextView tvTime;
    Button btnStartStop;
    Boolean allowTimer = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sbTimer = findViewById(R.id.sbTimer);

        final int max = 600;
        final int initVal = 30;

        sbTimer.setMax(max);
        sbTimer.setProgress(initVal);

        tvTime = findViewById(R.id.tvTime);
        Log.i("Starting time", "00:" + Integer.toString(initVal));

        tvTime.setText("00:" + Integer.toString(initVal));

        sbTimer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //int minutes = 0;
                //int seconds = 0;

                int minutes = progress / 60;
                int seconds = progress - (minutes * 60);
                String strMinutes = String.valueOf(minutes);
                String strSeconds = String.valueOf(seconds);

                if (seconds < 10) {
                    strSeconds = "0" + seconds;
                }
                if (minutes < 10) {
                    strMinutes = "0" + minutes;
                }

                String formattedTIme = strMinutes + ":" + strSeconds;
                Log.i("Formatted Time", formattedTIme);
                tvTime.setText(formattedTIme);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void startTimer(View view) {
        //Log.i("Timer", "Starting");
        if (allowTimer == true) {
            allowTimer = false;
            sbTimer.findViewById(R.id.sbTimer);

            new CountDownTimer(sbTimer.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    Log.i("Timer", String.valueOf(millisUntilFinished / 1000));

                    int milliSecondsAsInt = (int) millisUntilFinished / 1000;
                    sbTimer.setProgress(milliSecondsAsInt);

                    int minutes = milliSecondsAsInt / 60;
                    int seconds = milliSecondsAsInt - (minutes * 60);

                    String strMinutes = String.valueOf(minutes);
                    String strSeconds = String.valueOf(seconds);

                    if (seconds < 10) {
                        strSeconds = "0" + seconds;
                    }
                    if (minutes < 10) {
                        strMinutes = "0" + minutes;
                    }

                    Log.i("Time left", strMinutes + ":" + strSeconds);

                    String formattedTIme = strMinutes + ":" + strSeconds;
                    Log.i("Formatted Time", formattedTIme);
                    tvTime.setText(formattedTIme);
                }

                @Override
                public void onFinish() {
                    allowTimer = true;
                    Log.i("Timer", "Time ran out!");
                    playSound();
                }
            }.start();
        }

    }

    public void playSound() {
        Log.i("Sound", "Play sound");
        MediaPlayer mediaPlayer;
        //AudioManager audioManager;
        mediaPlayer = MediaPlayer.create(this, R.raw.beeping);
        mediaPlayer.start();
    }
}