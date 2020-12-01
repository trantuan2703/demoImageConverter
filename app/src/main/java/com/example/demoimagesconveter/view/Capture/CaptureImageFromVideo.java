package com.example.demoimagesconveter.view.Capture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.common.BaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CaptureImageFromVideo extends BaseActivity {
    public static final String KEY_GET_VIDEO="keygetvideo";
    public static final String KEY_GET_BACK_FOLDER="keygetbackfolder";
    ImageView imvBack;
    VideoView videoView;
    FloatingActionButton fabCptured;
    String videoPath,folderPath;
    MediaMetadataRetriever retriever;
    MediaController mediaController;

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
        mediaController = new MediaController(CaptureImageFromVideo.this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
    }

    private void registerevent() {
        imvBack.setOnClickListener(view -> {
            Intent intent = new Intent(CaptureImageFromVideo.this,ChooseVideoActivity.class);
            intent.putExtra(ChooseVideoActivity.KEY_GET_VIDEO_FOLDER_PATH,folderPath);
            startActivity(intent);
        });

        fabCptured.setOnClickListener(view -> {
            try {
                long currentDuration = videoView.getCurrentPosition()*1000;
                retriever.setDataSource(videoPath);
                Bitmap capturedBitmap = retriever.getFrameAtTime(currentDuration,MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                Log.d("TEST_CAPTURED",currentDuration+"s-"+capturedBitmap.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void init() {
        videoPath = getIntent().getStringExtra(KEY_GET_VIDEO);
        folderPath = getIntent().getStringExtra(KEY_GET_BACK_FOLDER);
        imvBack=findViewById(R.id.imv_capture_image_from_video_back);
        videoView=findViewById(R.id.vv_captured_video);
        fabCptured=findViewById(R.id.fab_capture);

        retriever = new MediaMetadataRetriever();
    }
}