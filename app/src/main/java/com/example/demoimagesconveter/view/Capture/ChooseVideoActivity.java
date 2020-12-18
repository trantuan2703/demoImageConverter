package com.example.demoimagesconveter.view.Capture;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.adapter.VideoAdapter;
import com.example.demoimagesconveter.common.BaseActivity;
import com.example.demoimagesconveter.model.ModelVideo;

import java.io.File;
import java.util.ArrayList;

public class ChooseVideoActivity extends BaseActivity implements VideoAdapter.onVideoClickListener{
    public static final String KEY_GET_VIDEO_FOLDER_PATH="videofolderpath";
    ImageView imvBack;
    RecyclerView rvVideos;
    ArrayList<ModelVideo> videos;
    String folderPath="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_video);
        init();
        registerevent();
    }

    private void registerevent() {
        imvBack.setOnClickListener(view -> finish());
    }

    private void init() {
        imvBack=findViewById(R.id.imv_choose_video_back);
        rvVideos=findViewById(R.id.rv_videos);
        folderPath = getIntent().getStringExtra(KEY_GET_VIDEO_FOLDER_PATH);
        getVideos(folderPath);
        fetchData();
    }

    private void fetchData() {
        VideoAdapter videoAdapter = new VideoAdapter(videos,this);
        rvVideos.setAdapter(videoAdapter);
        rvVideos.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
    }

    private void getVideos(String folderPath) {
        videos = new ArrayList<>();
        File path = new File(folderPath);
        File[] files = path.listFiles();
        if (files!=null){
            for (File file : files){
                if (file.getName().contains(".mp4")){
                    ModelVideo modelVideo = new ModelVideo();
                    modelVideo.setTitle(file.getName());
                    modelVideo.setPath(file.getAbsolutePath());
                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    retriever.setDataSource(this, Uri.fromFile(new File(modelVideo.getPath())));
                    String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                    modelVideo.setDuration(time);
                    modelVideo.setShowDuration(DisplayDuration(time));
                    videos.add(modelVideo);
                }
            }
        }
        for (int i = 0; i < videos.size(); i++) {
            Log.d("TEST_GET_VIDEO",videos.get(i).getTitle()+
                    " and path: "+videos.get(i).getPath()+
                    "and time: "+videos.get(i).getDuration());
        }
    }

    private String DisplayDuration(String duration) {
        int sec = Integer.parseInt(duration)/1000;
        String dispSec="";
        String dispMin="";
        if (sec<10){
            dispMin="00";
            dispSec="0"+sec;
        }else if(sec<60){
            dispMin="00";
            dispSec=""+sec;
        }else{
            int min = sec/60;
            sec%=60;
            if (min<10){
                dispMin="0"+min;
            }
            if (sec<10){
                dispSec="0"+sec;
            }
        }
        return dispMin+":"+dispSec;
    }

    @Override
    public void onClick(int pos) {
        Intent intent = new Intent(ChooseVideoActivity.this,CaptureImageFromVideo.class);
        intent.putExtra(CaptureImageFromVideo.KEY_GET_VIDEO,videos.get(pos));
        startActivity(intent);
    }
}