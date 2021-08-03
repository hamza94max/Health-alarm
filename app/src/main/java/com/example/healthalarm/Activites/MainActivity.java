package com.example.healthalarm.Activites;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
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
    Boolean enableStart;
    Boolean isStart = true;

    MediaPlayer stopWorking, backToWork;

    UltraViewPager viewPager;
    List <ViewpagerModel> photoslist;
    int [] photoscounts;

     OneTimeWorkRequest workrequest ;

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

    private void loadData() {
        photoslist =  PhotoDataSet.getPhotos();
        photoscounts = new int[photoslist.size()];
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
        public void start_btn(View view) {
         if (isStart){
             startMode();
             isStart = false;
         }else {
          stopMode();
          isStart = true;
    }}

    @SuppressLint("SetTextI18n")
    private void startMode(){
        startButton.setText(BtnTexts[0]);
        startButton.setBackgroundResource(R.drawable.button_shape_red);
        remainingTimetextview.setVisibility(View.VISIBLE);
        remainingTimetextview.setText("Working ..");
        //enableStart = true ;
        WorkManager.getInstance().enqueue(workrequest);
    }

    private void stopMode(){
        startButton.setText(BtnTexts[1]);
        startButton.setBackgroundResource(R.drawable.button_shape_green);
        remainingTimetextview.setVisibility(View.INVISIBLE);
        //enableStart = false ;
        WorkManager.getInstance().cancelAllWork();
    }



}
