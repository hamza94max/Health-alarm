package com.example.healthalarm.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.healthalarm.WorkManager.MyWorker;
import com.example.healthalarm.ViewPagerFuncations.ViewpagerFuncation;
import com.example.healthalarm.Adapters.SlideAdapter;
import com.example.healthalarm.DataSets.PhotoDataSet;
import com.example.healthalarm.Models.ViewpagerModel;
import com.example.healthalarm.R;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    TextView remainingTimetextview;
    String [] BtnTexts = { "Stop ","Start" };
    Boolean isStart,is = true;

    UltraViewPager viewPager;
    List <ViewpagerModel> photoslist;
    int [] photoscounts;

    int sharedstatus = 0;

    OneTimeWorkRequest workrequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        startButton = findViewById(R.id.start_btn);
        remainingTimetextview = findViewById(R.id.rmd_text);

        loadData();

        viewPager = findViewById(R.id.viewpager);
        SlideAdapter slideAdapter = new SlideAdapter( photoslist, getApplicationContext());
        viewPager.setAdapter(slideAdapter);
        ViewpagerFuncation viewpagerFuncation = new ViewpagerFuncation();
        viewpagerFuncation.setviewpager(viewPager);

        workrequest = new OneTimeWorkRequest.Builder(MyWorker.class).build();

    }

    private void checkstatus(int status) {
        if (status == 0){
            startMode();
        }else stopMode();

    }

    private void loadData() {
        photoslist =  PhotoDataSet.getPhotos();
        photoscounts = new int[photoslist.size()];
    }


    public void startbtn(View view) {
        if (is){
            sharedstatus = 1 ;
            startMode();
            setWorkerOn();
            is = false;
        }else {
            sharedstatus = 0 ;
            stopMode();
            is = true;
        }}

    @SuppressLint("SetTextI18n")
    private void startMode(){
        startButton.setText(BtnTexts[0]);
        startButton.setBackgroundResource(R.drawable.button_shape_red);
        remainingTimetextview.setVisibility(View.VISIBLE);
        remainingTimetextview.setText("Working ..");


    }

    private void setWorkerOn(){
        WorkManager.getInstance(getApplicationContext()).enqueue(workrequest);
    }

    private void stopMode(){
        startButton.setText(BtnTexts[1]);
        startButton.setBackgroundResource(R.drawable.button_shape_green);
        remainingTimetextview.setVisibility(View.INVISIBLE);

        WorkManager.getInstance(getApplicationContext()).cancelAllWork();

    }

 }