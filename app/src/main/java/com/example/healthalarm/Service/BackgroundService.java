package com.example.healthalarm.Service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;

public class BackgroundService extends Service {



    public BackgroundService() {
    }

    public int onStartCommand(Intent intent, int flags, int startId) {





        return START_STICKY;
    }
    public void onDestroy() {
        super.onDestroy();

      // StopMode();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}