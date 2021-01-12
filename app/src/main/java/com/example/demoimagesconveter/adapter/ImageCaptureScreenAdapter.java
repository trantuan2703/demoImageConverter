package com.example.demoimagesconveter.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ImageCaptureScreenAdapter extends FragmentStatePagerAdapter {

    List<Fragment> fragments;

    public ImageCaptureScreenAdapter(@NonNull FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void add(Fragment f){
        fragments.add(f);
    }


}
