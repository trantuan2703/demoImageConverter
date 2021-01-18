package com.example.demoimagesconveter.view.Gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.adapter.GalleryTabAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class GalleryActivity extends AppCompatActivity {
    TabLayout tlGallery;
    ViewPager vpGallery;
    ImageView imvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        init();
        registerevent();
    }

    private void registerevent() {
        imvBack.setOnClickListener(view -> finish());
    }
    private void init() {
        tlGallery = findViewById(R.id.tl_gallery);
        vpGallery = findViewById(R.id.vp_gallery);
        imvBack = findViewById(R.id.imv_gallery_back);
        setupfragment();
    }

    private void setupfragment() {
        GalleryTabAdapter galleryTabAdapter = new GalleryTabAdapter(getSupportFragmentManager());
        vpGallery.setAdapter(galleryTabAdapter);
        vpGallery.setOffscreenPageLimit(2);
        tlGallery.setupWithViewPager(vpGallery);
        Objects.requireNonNull(tlGallery.getTabAt(0)).setText("Images");
        Objects.requireNonNull(tlGallery.getTabAt(1)).setText("Video");
    }
}