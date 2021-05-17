package com.example.naruto_timer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    SeekBar sbNum;
    TextView tvTime;
    Button   gun,sound;
    DecimalFormat formatter = new DecimalFormat("00");
    int min =30,max=1800,minutes,seconds,g,ipos;
    MediaPlayer mediaPlayer;
    CountDownTimer countDownTimer;

    public void resetTime(int x)
    {
        gun.setText("GO ;)");g=0;sbNum.setEnabled(true);
        sbNum.setProgress(x);
        boom(x);
    }

    public void boom(int a )
    {
        minutes = a/60;seconds = a%60;

        tvTime.setText(formatter.format(minutes)+":"+formatter.format(seconds));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        sbNum = findViewById(R.id.sbTime);
        tvTime = findViewById(R.id.tvTime);
        gun = findViewById(R.id.button);
        sound = findViewById(R.id.btn2);
        sound.setVisibility(View.INVISIBLE);
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.naruto);


        Random random = new Random();
        int result =  random.nextInt(max-min) + min;
        sbNum.setMax(max);
        sbNum.setProgress(result);
        boom(result);

        sbNum.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                boom(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        gun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sound.setVisibility(View.INVISIBLE);
                if(g==0)
                {
                    gun.setText("STOP :(");g = 1;sbNum.setEnabled(false);
                    countDownTimer  = new CountDownTimer((sbNum.getProgress()*1000)+100,1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            ipos = (int) (millisUntilFinished/1000);
                            boom((int) (millisUntilFinished/1000));
                        }

                        @Override
                        public void onFinish() {
                            mediaPlayer.seekTo(0);
                            mediaPlayer.start();

                            sound.setVisibility(View.VISIBLE);
                            resetTime(min);
                        }
                    }.start();

                }
                else
                    {
                        resetTime(ipos);
                        mediaPlayer.pause();

                        countDownTimer.cancel();

                    }
            }
        });

        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();

                sound.setVisibility(View.INVISIBLE);
            }
        });








    }
}