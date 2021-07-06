package com.example.healthalarm.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.healthalarm.R;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
    }

    public void OpenApp(View view) {

        Intent intent = new Intent(getApplicationContext() , MainActivity.class);
        startActivity(intent);

    }
}