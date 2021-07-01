package com.example.healthalarm.DataSets;

import com.example.healthalarm.R;

import java.util.Locale;
import java.util.Random;

public class NotificationDataSet {




    static String [] NotificationData = {"Take a break ,myfriend" ,"Take care of your health" , "Don't forget to drink water"
            ,"Sit straight, don't slouch or slump, look straight ahead",
            "give your eyes a chance to reset their focus."













    };

    public static String getrandomAvice(){

        int randomNum = new Random().nextInt(NotificationData.length);
        return String.valueOf((NotificationData[randomNum]));
    }







}
