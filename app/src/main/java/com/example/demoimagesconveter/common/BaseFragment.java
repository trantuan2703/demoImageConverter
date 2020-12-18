package com.example.demoimagesconveter.common;

import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.demoimagesconveter.model.ModelVideo;
import com.example.demoimagesconveter.view.Capture.CaptureImageFromVideo;

public class BaseFragment extends Fragment {
    ModelVideo modelVideo;
    public void showToast(String s){
        Toast.makeText(getContext(), ""+s, Toast.LENGTH_SHORT).show();
    }

    public ModelVideo getVideoFromActivity(){
        CaptureImageFromVideo activity = (CaptureImageFromVideo) getActivity();
        if (activity!=null){
            modelVideo = activity.getVideoFromActivity();
        }
        return modelVideo;
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
    public String getEndType(String type){
        if (type.equals("JPEG")){
            return ".jpeg";
        }else{
            return ".png";
        }
    }
    public int getQuality(String quality){
        if(quality.equals("Best")){
            return 100;
        }
        if(quality.equals("Very High")){
            return 90;
        }
        if(quality.equals("High")){
            return 85;
        }
        if(quality.equals("Medium")){
            return 80;
        }
        if(quality.equals("Low")){
            return 70;
        }
        return 85;
    }

}
