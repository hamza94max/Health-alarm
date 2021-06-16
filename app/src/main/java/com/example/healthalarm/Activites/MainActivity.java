package com.example.healthalarm.Activites;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.healthalarm.R;

public class MainActivity extends AppCompatActivity {

    String [] BtnTexts = {"Stop ", "Start"} ;
    int count = 0 ;
    int timerem = 5 ;
    Boolean enableStart ;
    TextView btn , remainingtext ;
    MediaPlayer StopWorking , BackToWork;


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
            btn.setText(BtnTexts[0]);
            btn.setBackgroundResource(R.drawable.button_shape_red);
            remainingtext.setVisibility(View.VISIBLE);
            enableStart = true ;
            StartCountingTime();
        }
        private void StopMode(){
            btn.setText(BtnTexts[1]);
            btn.setBackgroundResource(R.drawable.button_shape_green);
            remainingtext.setVisibility(View.INVISIBLE);
            enableStart = false ;
        }
        private void StartCountingTime (){

        new CountDownTimer( timerem  * 1000, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                String timeremaining = TimetoString(millisUntilFinished);
                remainingtext.setText( getString(R.string.Timeremaining)+" "+ timeremaining);
                if (!enableStart)  StopMode();

            }

            public void onFinish() {
                if (enableStart){
                    PlaySound();
                    StopMode();

                }
            }
        }.start();
    }
        private void PlaySound (){
            StopWorking = MediaPlayer.create(getApplicationContext(),R.raw.rest);
            StopWorking.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    delay(18);

                }
            });
            StopWorking.start();
        }
        private void StopSound (){
            StopWorking.stop();
        }

}