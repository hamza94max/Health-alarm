package com.example.healthalarm.Activites;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.healthalarm.Adapters.SlideAdapter;
import com.example.healthalarm.DataSets.PhotoDataSet;
import com.example.healthalarm.Models.ViewpagerModel;
import com.example.healthalarm.R;
import com.example.healthalarm.ViewPagerFuncations.ViewpagerFuncation;
import com.example.healthalarm.WorkManager.MyWorker;
import com.example.healthalarm.databinding.ActivityMainBinding;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    ActivityMainBinding binding;

    String[] BtnTexts = {"Stop ", "Start"};
    Boolean isWorkingsharedpreference, isWorking;

    List<ViewpagerModel> photoslist;
    int[] Photoscounts;

    PeriodicWorkRequest workrequest;

    @SuppressLint("CommitPrefEdits")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);

        isWorkingsharedpreference = sharedpreferences.getBoolean("key",true);
        isWorking = sharedpreferences.getBoolean("isworking",false);

        checkstatus(isWorkingsharedpreference);

        editor = sharedpreferences.edit();

        LoadData();


        SlideAdapter slideAdapter = new SlideAdapter( photoslist, getApplicationContext());
        binding.viewpager.setAdapter(slideAdapter);
        ViewpagerFuncation viewpagerFuncation = new ViewpagerFuncation();
        //viewpagerFuncation.setviewpager(binding.viewpager);

        workrequest = new PeriodicWorkRequest.Builder(MyWorker.class,3, TimeUnit.SECONDS).
                    setInitialDelay(Duration.ofSeconds(2))
                    .setConstraints(new Constraints.Builder().setRequiresDeviceIdle(false).build())
                    .addTag("work")
                    .build();

    }

    private void LoadData() {
        photoslist =  PhotoDataSet.getPhotos();
        Photoscounts = new int[photoslist.size()];
    }

    public void startbtn(View view) {
        if (isWorking) {
            startMode();
            setWorkerOn();
            isWorking = false;

        } else {
            stopMode();
            setWorkerOff();
            isWorking = true;
        }
        editor.putBoolean("key", isWorking);
        editor.putBoolean("isworking", isWorking);
        editor.apply();
    }

    @SuppressLint("SetTextI18n")
    private void startMode() {
        binding.startBtn.setText(BtnTexts[0]);
        binding.startBtn.setBackgroundResource(R.drawable.button_shape_red);
        binding.remainingTimeTextview.setVisibility(View.VISIBLE);
        binding.remainingTimeTextview.setText("Working ..");
    }

    private void setWorkerOn(){
        WorkManager.getInstance(getApplicationContext()).
                enqueueUniquePeriodicWork("workk", ExistingPeriodicWorkPolicy.REPLACE, workrequest);
    }

    private void stopMode() {
        binding.startBtn.setText(BtnTexts[1]);
        binding.startBtn.setBackgroundResource(R.drawable.button_shape_green);
        binding.remainingTimeTextview.setVisibility(View.INVISIBLE);
    }

    private void setWorkerOff(){
        WorkManager.getInstance(getApplicationContext()).cancelUniqueWork("workk");
    }

    private void checkstatus(Boolean status){
        if (status){
            stopMode();
        }else{
            startMode();
        }
    }

 }