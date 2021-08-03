package com.example.healthalarm.WorkManager;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.healthalarm.DataSets.NotificationDataSet;
import com.example.healthalarm.R;


public class MyWorker extends Worker {

    Boolean enableStart = true;
    MediaPlayer stopWorking, backToWork;
    CountDownTimer mCountDownTimer;

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        startCountingTime();
        return Result.success();
    }

    private void startCountingTime(){

        try {
            Thread.sleep(5000);
            displayNotification("","hamza");
            playSound();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    @SuppressLint("DefaultLocale")
    private String timetoString (long millisUntilFinished){
        int minutes = (int) (millisUntilFinished / (60 * 1000));
        int seconds = (int) ((millisUntilFinished / 1000) % 60);
        return String.format("%d:%02d", minutes, seconds);
    }

    private void playSound (){
        int breaktime = 5;
        stopWorking = MediaPlayer.create(getApplicationContext(),R.raw.rest);
        stopWorking.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                try {
                    Thread.sleep(breaktime * 1000);
                    backtoWorkSound();
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startCountingTime();
            }
        });
        stopWorking.start();
    }

    private void backtoWorkSound(){
        backToWork = MediaPlayer.create(getApplicationContext(),R.raw.back);
        backToWork.start();
    }

    private void stopSounds(){
        if (Soundisplaying()){
            backToWork.stop();
            stopWorking.stop();
        }}

    private Boolean Soundisplaying(){
        return backToWork != null || stopWorking != null;
    }

    private void displayNotification(String title, String task) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel("Channel 1", "Channel 1", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "simplifiedcoding")
                .setContentTitle(title)
                .setContentText(task)
                .setSmallIcon(R.drawable.splash)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(uri);


        notificationManager.notify(1, notification.build());
    }

}
