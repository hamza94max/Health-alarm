package com.example.healthalarm.DataSets;


import java.util.Random;

public class NotificationDataSet {

    static String [] NotificationData = {
            "Take a break, myfriend" ,
            "Take care of your health" ,
            "Don't forget to drink water",
            "Sit straight, don't slouch or slump, look straight ahead",
            "Give your eyes a chance to reset their focus.",
            "Remember to check your posture",
            "sit up straight, be healthy.",
            "How's your posture, friend",
            "Check your posture, buddy",
            "Check yourself, mate"

    };

    public static String getrandomAvice(){

        int randomNum = new Random().nextInt(NotificationData.length);
        return String.valueOf((NotificationData[randomNum]));
    }
}
