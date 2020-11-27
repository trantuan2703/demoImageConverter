package com.example.demoimagesconveter.view;

import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.common.BaseActivity;

public class MainActivity extends BaseActivity {

    CardView cvOpenVidGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        registerEvent();
    }

    private void registerEvent() {
        cvOpenVidGallery.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, OpenVideoFolders.class);
            startActivity(intent);
        });
    }

    private void init() {
        cvOpenVidGallery=findViewById(R.id.cv_open_gallery);
    }
}