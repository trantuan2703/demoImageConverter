package com.example.demoimagesconveter.view.Capture;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.common.BaseFragment;
import com.example.demoimagesconveter.model.ModelVideo;

public class AutomaticallyFragment extends BaseFragment {
    VideoView videoView;
    TextView tvTitle;
    ModelVideo modelVideo;
    public AutomaticallyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_automatically, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        fetchVideo();
        registerevent();
    }
    private void registerevent() {
        videoView.setVideoPath(modelVideo.getPath());
        tvTitle.setText(modelVideo.getTitle().substring(0,modelVideo.getTitle().length()-4));
        videoView.start();
    }
    private void fetchVideo() {
        Log.d("TEST", "AutomaticallyFragment " + modelVideo);
        if (modelVideo == null)
            return;
        videoView.setMediaController(new MediaController(getContext()));
    }

    private void init(View view) {
        videoView=view.findViewById(R.id.vv_automatically_captured_video);
        tvTitle=view.findViewById(R.id.tv_auto_cap_title);
        modelVideo = getVideoFromActivity();
    }

}