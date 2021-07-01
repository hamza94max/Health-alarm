package com.example.healthalarm.Activites;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthalarm.Classes.ViewpagerFuncation;
import com.example.healthalarm.Adapters.SlideAdapter;
import com.example.healthalarm.DataSets.PhotoDataSet;
import com.example.healthalarm.Models.Model;
import com.example.healthalarm.R;
import com.example.healthalarm.Service.BackgroundService;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int count = 0 ;
    int breaktime = 5 ;
    Button StartButton ;
    TextView RemainingTimetext ;
    String [] BtnTexts = { "Stop ", "Start" } ;
    Boolean enableStart ;
    int timeremaining = 5 ;
    MediaPlayer StopWorking , BackToWork ;

    UltraViewPager viewPager ;
    List <Model> photoslist ;
    int [] photoscounts ;



    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    Calendar calendar ;

    @Override
    public boolean isDestroyed() {

        // TODO When the app is closed .
        Toast.makeText(this, "destroyed !", Toast.LENGTH_LONG).show();
        //startService(new Intent(this , BackgroundService.class));
        return super.isDestroyed();
    }

    @Override
    protected void onStop() {
        Toast.makeText(this, "Stopped !", Toast.LENGTH_SHORT).show();

        super.onStop();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StartButton = findViewById(R.id.start_btn);
        RemainingTimetext = findViewById(R.id.rmd_text);

        /*alarmMgr = (AlarmManager)getApplicationContext().getSystemService(getApplicationContext().ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
        alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
        calendar = Calendar.getInstance();*/


        loadData();


        viewPager = findViewById(R.id.viewpager);
        SlideAdapter slideAdapter = new SlideAdapter( photoslist , getApplicationContext());
        viewPager.setAdapter(slideAdapter);
        ViewpagerFuncation viewpagerFuncation = new ViewpagerFuncation();
        viewpagerFuncation.ViewpagerFuncations(viewPager);



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
        return String.format("%d:%02d", minutes, seconds);
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
        //alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),1000 * 4 , alarmIntent);

        new CountDownTimer( timeremaining  * 1000, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                String timeremaining = TimetoString(millisUntilFinished);
                RemainingTimetext.setText( getString(R.string.Timeremaining)+" "+ timeremaining);
                if (!enableStart) {StopMode();} }

            public void onFinish() {
                if (enableStart){
                    PlaySound();
                    startService(new Intent( getApplicationContext() , BackgroundService.class));
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

                        Thread.sleep(breaktime * 1000);
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

}
