package com.example.demoimagesconveter.common;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    public void showtoast(String s){
        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
    }
}
