package com.example.healthalarm.Activites;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.healthalarm.Adapters.SlideAdapter;
import com.example.DataSets.PhotoDataSet;
import com.example.healthalarm.Models.Model;
import com.example.healthalarm.R;
import com.example.healthalarm.Receiver.NotificationReceiver;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int count = 0 ;
    Button StartButton ;
    TextView RemainingTimetext ;
    String [] BtnTexts = { "Stop ", "Start" } ;
    Boolean enableStart ;
    int timerem = 5 ;
    MediaPlayer StopWorking , BackToWork ;

    List <Model> photoslist ;
    int [] photoscounts ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StartButton = findViewById(R.id.start_btn);
        RemainingTimetext = findViewById(R.id.rmd_text);


        loadData();


        final ViewPager viewPager = findViewById(R.id.viewpager);
        SlideAdapter slideAdapter = new SlideAdapter(photoslist , getApplicationContext());
        viewPager.setAdapter(slideAdapter);
        

    }





    private void loadData() {
        photoslist =  PhotoDataSet.getPhotos();
        photoscounts = new int[photoslist.size()];
    }


        @RequiresApi(api = Build.VERSION_CODES.M)
        public void start_btn(View view) {
            count ++ ;
            if (count % 2 == 0){
                StopMode();
            //stopService(new Intent(this , BackgroundService.class));
            }else {
            StartMode();
            //startService(new Intent(this , BackgroundService.class));

            }
        }
    private String TimetoString (long millisUntilFinished){

        int minutes = (int) (millisUntilFinished / (60 * 1000));
        int seconds = (int) ((millisUntilFinished / 1000) % 60);
        @SuppressLint("DefaultLocale")
        String timetostring = String.format("%d:%02d", minutes, seconds);
        return timetostring ;
    }

    private void StartMode(){
        StartButton.setText(BtnTexts[0]);
        StartButton.setBackgroundResource(R.drawable.button_shape_red);
        RemainingTimetext.setVisibility(View.VISIBLE);
        enableStart = true ;
        StartCountingTime();

    }
    private void StopMode(){
        StartButton.setText(BtnTexts[1]);
        StartButton.setBackgroundResource(R.drawable.button_shape_green);
        RemainingTimetext.setVisibility(View.INVISIBLE);
        enableStart = false ;

    }
    public void StartCountingTime(){

        new CountDownTimer( timerem  * 1000, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                String timeremaining = TimetoString(millisUntilFinished);
                RemainingTimetext.setText( getString(R.string.Timeremaining)+" "+ timeremaining);
                if (!enableStart) {StopMode();} }

            public void onFinish() {
                if (enableStart){
                    //addNotification();
                    PlaySound();
                }
            }
        }.start();
    }
    private void PlaySound (){
        StopWorking = MediaPlayer.create(getApplicationContext(),R.raw.rest);
        StopWorking.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                try {
                    Thread.sleep(5000);
                    BacktoWorkSound();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                StartCountingTime();
            }
        });
        StopWorking.start();
    }
    private void BacktoWorkSound(){
        BackToWork = MediaPlayer.create(getApplicationContext(),R.raw.back);
        BackToWork.start();
    }

    public void addNotification (){

        // TODO Notification
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() , pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }


    }



    }
