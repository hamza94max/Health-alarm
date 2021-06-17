package com.example.healthalarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.healthalarm.Activites.MainActivity;

import java.util.Random;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent openapp = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, openapp, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,context.getString(R.string.app_name))
                .setSmallIcon(R.drawable.splash)
                .setAutoCancel(true)
                .setContentText(getrandomAvice());

        Intent notificationIntent = new Intent(context, MainActivity.class);

        pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);


        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(uri);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        NotificationManagerCompat.from(context).notify(0, notification);

    }

    int [] NotificationData = {(R.string.Advice1),(R.string.Advice2), (R.string.Advice3)};

    private String getrandomAvice (){

        int randomNum = new Random().nextInt(NotificationData.length);
        String randomAdvice = String.valueOf((NotificationData[randomNum]));

        return randomAdvice;
    }



}
