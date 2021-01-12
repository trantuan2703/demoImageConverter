package com.example.demoimagesconveter.view.Capture;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.adapter.ImageCaptureScreenAdapter;
import com.example.demoimagesconveter.common.BaseActivity;
import com.example.demoimagesconveter.model.ModelVideo;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class CaptureImageFromVideo extends BaseActivity{
    public static final String KEY_GET_VIDEO="keygetvideo";
    ImageView imvBack;
    ModelVideo modelVideo;
    ImageCaptureScreenAdapter imageCaptureScreenAdapter;
    ViewPager vpImageCaptureScreen;
    TabLayout tlImageCaptureScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image_from_video);
        init();
        registerevent();
    }

    public ModelVideo getVideoFromActivity(){
        return modelVideo;
    }

    private void registerevent() {
        imvBack.setOnClickListener(view -> finish());
    }

    private void init() {
        if (getIntent().hasExtra(KEY_GET_VIDEO)){
            modelVideo = (ModelVideo) getIntent().getSerializableExtra(KEY_GET_VIDEO);
        }
        imvBack=findViewById(R.id.imv_capture_image_from_video_back);
        tlImageCaptureScreen = findViewById(R.id.tl_capture_type);
        vpImageCaptureScreen=findViewById(R.id.vp_capture_image_from_video);
        setUpFragment();
    }



    private void setUpFragment() {
        imageCaptureScreenAdapter = new ImageCaptureScreenAdapter(getSupportFragmentManager());
        QuickCaptureFragment quickCaptureFragment = new QuickCaptureFragment();
        AutomaticallyFragment automaticallyFragment = new AutomaticallyFragment();
        imageCaptureScreenAdapter.add(quickCaptureFragment);
        imageCaptureScreenAdapter.add(automaticallyFragment);
        vpImageCaptureScreen.setAdapter(imageCaptureScreenAdapter);
        vpImageCaptureScreen.setOffscreenPageLimit(2);
        tlImageCaptureScreen.setupWithViewPager(vpImageCaptureScreen);

        Objects.requireNonNull(tlImageCaptureScreen.getTabAt(0)).setText("Quick");
        Objects.requireNonNull(tlImageCaptureScreen.getTabAt(1)).setText("Automatically");
        vpImageCaptureScreen.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        automaticallyFragment.videoView.pause();
                        quickCaptureFragment.videoViewQuickCapture.start();
                        break;
                    case 1:
                        quickCaptureFragment.videoViewQuickCapture.pause();
                        automaticallyFragment.videoView.start();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}