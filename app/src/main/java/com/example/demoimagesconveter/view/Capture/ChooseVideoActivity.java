package com.example.demoimagesconveter.view.Capture;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.adapter.VideoAdapter;
import com.example.demoimagesconveter.common.BaseActivity;
import com.example.demoimagesconveter.model.modelVideo;

import java.io.File;
import java.util.ArrayList;

public class ChooseVideoActivity extends BaseActivity implements VideoAdapter.onVideoClickListener{
    public static final String KEY_GET_VIDEO_FOLDER_PATH="videofolderpath";
    ImageView imvBack;
    RecyclerView rvVideos;
    ArrayList<modelVideo> videos;
    String folderPath="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_video);
        init();
        registerevent();
    }

    private void registerevent() {
        imvBack.setOnClickListener(view -> {
            Intent intent = new Intent(ChooseVideoActivity.this,OpenVideoFolders.class);
            startActivity(intent);
        });
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
                    modelVideo modelVideo = new modelVideo();
                    modelVideo.setTitle(file.getName());
                    modelVideo.setPath(file.getAbsolutePath());
                    modelVideo.setThumbNail(ThumbnailUtils.createVideoThumbnail(String.valueOf(file.getAbsoluteFile()), MediaStore.Images.Thumbnails.MINI_KIND));
                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    retriever.setDataSource(this, Uri.fromFile(new File(modelVideo.getPath())));
                    String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                    modelVideo.setDuration(time);
                    videos.add(modelVideo);
                }
            }
        }
        for (int i = 0; i < videos.size(); i++) {
            Log.d("TEST_GET_VIDEO",videos.get(i).getTitle()+
                    " and path: "+videos.get(i).getPath()+
                    "and bitmap: "+videos.get(i).getThumbNail().toString()+
                    "and time: "+videos.get(i).getDuration());
        }
    }

    @Override
    public void onClick(int pos) {
        Intent intent = new Intent(ChooseVideoActivity.this,CaptureImageFromVideo.class);
        intent.putExtra(CaptureImageFromVideo.KEY_GET_VIDEO,videos.get(pos).getPath());
        intent.putExtra(CaptureImageFromVideo.KEY_GET_BACK_FOLDER,folderPath);
        startActivity(intent);
    }
}