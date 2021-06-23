package com.example.healthalarm.Activites;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.example.healthalarm.Receiver.NotificationReceiver;
import com.example.healthalarm.R;
import com.example.healthalarm.Service.BackgroundService;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

        int count = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

        @RequiresApi(api = Build.VERSION_CODES.M)
        public void start_btn(View view) {
            count ++ ;
            if (count % 2 == 0){
            stopService(new Intent(this , BackgroundService.class));
            }else {
            startService(new Intent(this , BackgroundService.class));

            }
        }




    }
