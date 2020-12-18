package com.example.demoimagesconveter.view.Gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.common.BaseFragment;

public class GalleryVideosFragment extends BaseFragment {
    public GalleryVideosFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery_videos, container, false);
    }
}