package com.example.healthalarm.Service;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.healthalarm.R;
import com.example.healthalarm.Receiver.NotificationReceiver;

import java.util.Calendar;

public class BackgroundService extends Service {

    String [] BtnTexts = {"Stop ", "Start"} ;
    Boolean enableStart ;
    TextView btn , remainingtext ;
    int timerem = 5 ;
    LayoutInflater inflater;

    MediaPlayer StopWorking , BackToWork;

    public BackgroundService() {
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        View view = LayoutInflater.from (this).inflate(R.layout.activity_main, null);
        btn = view.findViewById(R.id.start_btn);
        remainingtext = view.findViewById(R.id.rmd_text);
        StartMode();





        return START_STICKY;
    }
    public void onDestroy() {
        super.onDestroy();

        StopMode();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private String TimetoString (long millisUntilFinished){

        int minutes = (int) (millisUntilFinished / (60 * 1000));
        int seconds = (int) ((millisUntilFinished / 1000) % 60);
        @SuppressLint("DefaultLocale")
        String timetostring = String.format("%d:%02d", minutes, seconds);
        return timetostring ;
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
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }


    }}