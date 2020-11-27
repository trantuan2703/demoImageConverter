package com.example.demoimagesconveter.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.common.BaseActivity;

public class CaptureImageFromVideo extends BaseActivity {
    public static final String KEY_GET_VIDEO="keygetvideo";
    public static final String KEY_GET_BACK_FOLDER="keygetbackfolder";
    ImageView imvBack;
    VideoView videoView;
    String videoPath,folderPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image_from_video);
        init();
        fetchVideo();
        registerevent();
    }

    private void fetchVideo() {
        videoView.setVideoPath(videoPath);
        videoView.start();
        MediaController mediaController = new MediaController(CaptureImageFromVideo.this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
    }

    private void registerevent() {
        imvBack.setOnClickListener(view -> {
            Intent intent = new Intent(CaptureImageFromVideo.this,ChooseVideoActivity.class);
            intent.putExtra(ChooseVideoActivity.KEY_GET_VIDEO_FOLDER_PATH,folderPath);
            startActivity(intent);
        });
    }

    private void init() {
        videoPath = getIntent().getStringExtra(KEY_GET_VIDEO);
        folderPath = getIntent().getStringExtra(KEY_GET_BACK_FOLDER);
        imvBack=findViewById(R.id.imv_capture_image_from_video_back);
        videoView=findViewById(R.id.vv_captured_video);
    }
}