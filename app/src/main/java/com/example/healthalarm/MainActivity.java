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
import android.widget.TimePicker;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    String [] Btn_texts = {"Stop ", "Start"};
    int count = 0;
    TextView btn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.start_btn);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void start_btn(View view) {
        count ++ ;

        if (count % 2 == 0){
            btn.setText(Btn_texts[1]);
        }else {
            btn.setText(Btn_texts[0]);
        }

        TextView remainingtext = findViewById(R.id.rmd_text);





            new CountDownTimer( 10 * 1000, 1000) {


                public void onTick(long millisUntilFinished) {

                    int minutes = (int) (millisUntilFinished / (60 * 1000));
                    int seconds = (int) ((millisUntilFinished / 1000) % 60);
                    String str = String.format("%d:%02d", minutes, seconds);
                    remainingtext.setText("Time remaining: " + str);
                }

                public void onFinish() {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // code that recount the time
                            Toast.makeText(MainActivity.this, "done!", Toast.LENGTH_SHORT).show();
                        }
                    }, 3000);
                }
            }.start();



    }
}