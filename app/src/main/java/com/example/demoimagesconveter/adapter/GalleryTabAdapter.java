package com.example.demoimagesconveter.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.demoimagesconveter.view.Gallery.GalleryImagesFragment;
import com.example.demoimagesconveter.view.Gallery.GalleryVideosFragment;

public class GalleryTabAdapter extends FragmentPagerAdapter {
    public GalleryTabAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return new GalleryImagesFragment();
        }else{
            return new GalleryVideosFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
