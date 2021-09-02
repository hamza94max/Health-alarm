package com.example.healthalarm.Activites;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthalarm.WorkManager.MyWorker;
import com.example.healthalarm.ViewPagerFuncations.ViewpagerFuncation;
import com.example.healthalarm.Adapters.SlideAdapter;
import com.example.healthalarm.DataSets.PhotoDataSet;
import com.example.healthalarm.Models.ViewpagerModel;
import com.example.healthalarm.R;
import com.tmall.ultraviewpager.UltraViewPager;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor ;

    Button startButton;
    TextView remainingTimetextview;
    String [] BtnTexts = { "Stop ","Start" };
    Boolean sharedpreference,isWorking = true;

    UltraViewPager viewPager;
    List <ViewpagerModel> photoslist;
    int [] Photoscounts;

    PeriodicWorkRequest workrequest;

    @SuppressLint("CommitPrefEdits")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);

        sharedpreference = sharedpreferences.getBoolean("key",true);

        checkstatus(sharedpreference);



        editor = sharedpreferences.edit();

        startButton = findViewById(R.id.start_btn);
        remainingTimetextview = findViewById(R.id.rmd_text);

        LoadData();

        viewPager = findViewById(R.id.viewpager);
        SlideAdapter slideAdapter = new SlideAdapter( photoslist, getApplicationContext());
        viewPager.setAdapter(slideAdapter);
        ViewpagerFuncation viewpagerFuncation = new ViewpagerFuncation();
        viewpagerFuncation.setviewpager(viewPager);

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
        if (isWorking){
            startMode();
            isWorking = false;
            sharedpreference = false;
            editor.putBoolean("key", sharedpreference);
            editor.apply();

        }else {
            stopMode();
            isWorking = true;
            sharedpreference = true;
            editor.putBoolean("key", sharedpreference);
            editor.apply();
        }}

    @SuppressLint("SetTextI18n")
    private void startMode(){
        startButton.setText(BtnTexts[0]);
        startButton.setBackgroundResource(R.drawable.button_shape_red);
        remainingTimetextview.setVisibility(View.VISIBLE);
        remainingTimetextview.setText("Working ..");
        setWorkerOn();
    }

    private void setWorkerOn(){
        WorkManager.getInstance(getApplicationContext()).
                enqueueUniquePeriodicWork("workk",ExistingPeriodicWorkPolicy.REPLACE,workrequest);
    }

    private void stopMode(){
        startButton.setText(BtnTexts[1]);
        startButton.setBackgroundResource(R.drawable.button_shape_green);
        remainingTimetextview.setVisibility(View.INVISIBLE);

        WorkManager.getInstance(getApplicationContext()).cancelUniqueWork("workk");

    }

    private void checkstatus(Boolean status){
        if (status){
            Toast.makeText(this, "true", Toast.LENGTH_SHORT).show();
            //startMode();
        }else{
            Toast.makeText(this, "false", Toast.LENGTH_SHORT).show();
        }
    }

 }