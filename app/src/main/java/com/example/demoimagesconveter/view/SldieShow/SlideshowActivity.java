package com.example.demoimagesconveter.view.SldieShow;

import android.os.Bundle;

import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.common.BaseActivity;

import java.util.ArrayList;

public class SlideshowActivity extends BaseActivity {
    public static final String KEY_GET_IMAGES_TO_CREATE_SLIDE_SHOW="KEY_GET_IMAGES_TO_CREATE_SLIDE_SHOW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);
        init();
    }

    private void init() {
        ArrayList<String> paths = getIntent().getStringArrayListExtra(KEY_GET_IMAGES_TO_CREATE_SLIDE_SHOW);
        showtoast(""+paths.size());
    }
}