package com.example.healthalarm.Activites;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.example.healthalarm.NotificationReceiver;
import com.example.healthalarm.R;

import java.util.Calendar;

import static com.example.healthalarm.App.CHANNEL_1_ID;

public class MainActivity extends AppCompatActivity {

    String [] BtnTexts = {"Stop ", "Start"} ;
    int count = 0 ;
    int timerem = 5 ;
    Boolean enableStart ;
    TextView btn , remainingtext ;
    MediaPlayer StopWorking , BackToWork;

    private NotificationManagerCompat notificationManager;


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
            StopSound();
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
                   addNotification();
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
        private void StopSound (){
            StopWorking.stop();
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



    }
}