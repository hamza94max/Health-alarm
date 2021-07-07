package com.example.healthalarm.Activites;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.healthalarm.ViewPagerFuncations.ViewpagerFuncation;
import com.example.healthalarm.Adapters.SlideAdapter;
import com.example.healthalarm.DataSets.PhotoDataSet;
import com.example.healthalarm.Models.ViewpagerModel;
import com.example.healthalarm.R;
import com.example.healthalarm.Services.BackgroundService;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    int breaktime = 5;
    Button startButton;
    TextView remainingTimetext;
    String [] BtnTexts = { "Stop ","Start" };
    Boolean enableStart , isStart = true;
    int timeremaining = 5;
    MediaPlayer stopWorking , backToWork;

    UltraViewPager viewPager;
    List <ViewpagerModel> photoslist;
    int [] photoscounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.start_btn);
        remainingTimetext = findViewById(R.id.rmd_text);

        loadData();

        viewPager = findViewById(R.id.viewpager);
        SlideAdapter slideAdapter = new SlideAdapter( photoslist , getApplicationContext());
        viewPager.setAdapter(slideAdapter);
        ViewpagerFuncation viewpagerFuncation = new ViewpagerFuncation();
        viewpagerFuncation.setviewpager(viewPager);

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
                }
        }

    private void startMode(){
        startButton.setText(BtnTexts[0]);
        startButton.setBackgroundResource(R.drawable.button_shape_red);
        remainingTimetext.setVisibility(View.VISIBLE);
        enableStart = true ;
        startCountingTime();
    }

    private void stopMode(){
        startButton.setText(BtnTexts[1]);
        startButton.setBackgroundResource(R.drawable.button_shape_green);
        remainingTimetext.setVisibility(View.INVISIBLE);
        enableStart = false ;
    }

    public void startCountingTime(){
        new CountDownTimer( timeremaining  * 1000, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                String timeremaining = timetoString(millisUntilFinished);
                remainingTimetext.setText(getString(R.string.Timeremaining)+" "+ timeremaining);
                if (!enableStart) {stopMode();} }

            public void onFinish() {
                if (enableStart){
                    playSound();
                    startService(new Intent(getApplicationContext() , BackgroundService.class));
                }
            }
        }.start();
    }

    @SuppressLint("DefaultLocale")
    private String timetoString (long millisUntilFinished){

        int minutes = (int) (millisUntilFinished / (60 * 1000));
        int seconds = (int) ((millisUntilFinished / 1000) % 60);
        return String.format("%d:%02d", minutes, seconds);
    }

    private void playSound (){
        stopWorking = MediaPlayer.create(getApplicationContext(),R.raw.rest);
        stopWorking.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                try {
                Thread.sleep(breaktime * 1000);
                backtoWorkSound();
                } catch (InterruptedException e) {
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

}
