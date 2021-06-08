package com.example.healthalarm;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String [] Btn_texts = {"Stop ", "Start"} ;
    int count = 0 ;
    Boolean enableStart ;
    TextView btn , remainingtext ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.start_btn);
        remainingtext = findViewById(R.id.rmd_text);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void start_btn(View view) {
        count ++ ;

        if (count % 2 == 0){
            btn.setText(Btn_texts[1]);
            remainingtext.setVisibility(View.INVISIBLE);
            enableStart = false ;



        }else {

            // TODO Start State
            btn.setText(Btn_texts[0]);
            remainingtext.setVisibility(View.VISIBLE);
            enableStart = true ;
            StartTime();

        }
    }

    private void StartTime (){



        new CountDownTimer( 8 * 1000, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {

                int minutes = (int) (millisUntilFinished / (60 * 1000));
                int seconds = (int) ((millisUntilFinished / 1000) % 60);
                @SuppressLint("DefaultLocale")
                String str = String.format("%d:%02d", minutes, seconds);
                remainingtext.setText( getString(R.string.Timeremaining)+" "+ str);


            }

            public void onFinish() {
                if (enableStart){
                    delay(2);
                }}

        }.start();
    }
    private void delay (int seconds){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // code that recount the time
                Toast.makeText(MainActivity.this, "done!", Toast.LENGTH_SHORT).show();
            }
        }, seconds*1000);
    }}