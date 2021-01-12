package com.example.demoimagesconveter.common;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    public void showtoast(String s){
        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
    }
    public String MilliSecondsToTimer(String duration){
        long millSec = Long.parseLong(duration);
        String finalTimerString;
        String hoursString = "";
        String secondString;
        String minuteString;
        int seconds = (int) (millSec / 1000) % 60 ;
        int minutes = (int) ((millSec / (1000*60)) % 60);
        int hours   = (int) ((millSec / (1000*60*60)) % 24);
        if (hours > 0) {
            hoursString = hours + ":";
        }
        if (seconds < 10) {
            secondString = "0" + seconds;
        } else secondString = "" + seconds;
        if (minutes < 10) {
            minuteString = "0" + minutes;
        } else minuteString = "" + minutes;
        finalTimerString = hoursString + minuteString + ":" + secondString;
        return finalTimerString;
    }
}
