package com.example.healthalarm.WorkManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.healthalarm.DataSets.NotificationDataSet;
import com.example.healthalarm.R;

public class MyWorker extends Worker {

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
            Thread.sleep(5 * 1000);
            displayNotification(NotificationDataSet.getrandomAvice());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startCountingTime();
    }

    private void displayNotification(String content) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().
                getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel("Channel 1", "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "Channel 1")
                .setContentText(content)
                .setSmallIcon(R.drawable.splash)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(uri);

        notificationManager.notify(1, notification.build());
    }

    @Override
    public void onStopped() {
        super.onStopped();
    }
}
