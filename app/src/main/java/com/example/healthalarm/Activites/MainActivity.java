package com.example.healthalarm.Activites;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.healthalarm.R;

public class MainActivity extends AppCompatActivity {

    String [] Btn_texts = {"Stop ", "Start"} ;
    int count = 0 ;
    int timerem = 8;
    Boolean enableStart ;
    TextView btn , remainingtext ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.start_btn);
        remainingtext = findViewById(R.id.rmd_text);
        btn.setBackgroundColor(Color.GREEN);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void start_btn(View view) {
        count ++ ;
        if (count % 2 == 0){
        StopMode();
        }else {
            StartMode();

        }
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
        }
        private String TimetoString (long millisUntilFinished){

            int minutes = (int) (millisUntilFinished / (60 * 1000));
            int seconds = (int) ((millisUntilFinished / 1000) % 60);
            @SuppressLint("DefaultLocale")
            String timetostring = String.format("%d:%02d", minutes, seconds);
            return timetostring;
        }

        private void StartMode(){
            btn.setText(Btn_texts[0]);
            btn.setBackgroundColor(Color.RED);
            remainingtext.setVisibility(View.VISIBLE);
            enableStart = true ;
            StartTime();
        }
        private void StopMode(){
            btn.setText(Btn_texts[1]);
            btn.setBackgroundColor(Color.GREEN);
            remainingtext.setVisibility(View.INVISIBLE);
            enableStart = false ;
        }
        private void StartTime (){

        new CountDownTimer( timerem * 1000, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                String timeremaining = TimetoString(millisUntilFinished);
                remainingtext.setText( getString(R.string.Timeremaining)+" "+ timeremaining);
                if (!enableStart)  StopMode();

            }

            public void onFinish() {
                if (enableStart){
                    delay(2); }
            }
        }.start();
    }





}