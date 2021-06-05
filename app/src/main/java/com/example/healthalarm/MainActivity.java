package com.example.healthalarm;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void start_btn(View view) {

        int btn_count = 0;
        if(btn_count==5){
            Toast.makeText(this, "Your account has been blocked for 15 minutes due to 5 unsuccessfull attempts.", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "Start ", Toast.LENGTH_SHORT).show();
        @SuppressLint({"NewApi", "LocalSuppress"})

        Handler handler = new Handler();
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                        } catch (Exception e) {
                            // error, do something
                        }
                    }
                });
            }
        };

        timer.schedule(task, 0, 60);
        Toast.makeText(this, "End !", Toast.LENGTH_SHORT).show();





    }
}