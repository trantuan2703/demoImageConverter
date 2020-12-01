package com.example.demoimagesconveter.view.Capture;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.adapter.FolderAdapter;
import com.example.demoimagesconveter.common.BaseActivity;
import com.example.demoimagesconveter.model.modelFolder;
import com.example.demoimagesconveter.view.MainActivity;

import java.util.ArrayList;

public class OpenVideoFolders extends BaseActivity implements FolderAdapter.onFolderClickListener {

    ImageView imvBack;
    RecyclerView rvGalleries;
    private final ArrayList<modelFolder> videoFolders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_video_gallery);
        init();
        registerevent();
    }

    private void registerevent() {
        imvBack.setOnClickListener(view -> {
            Intent intent = new Intent(OpenVideoFolders.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void init() {
        rvGalleries=findViewById(R.id.rv_galleries);
        imvBack=findViewById(R.id.imv_open_video_gallery_back);
        getAllFolder();
    }

    private void getAllFolder() {
        ArrayList<String> videoPaths = new ArrayList<>();
        Uri allVideosuri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Video.VideoColumns.DATA, MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME};
        Cursor cursor = getContentResolver().query(allVideosuri, projection, null, null, null);
        try {
            cursor.moveToFirst();
            do {
                modelFolder fold = new modelFolder();
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
                String folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME));
                String datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                String folderpaths = datapath.replace(name, "");
                if (!videoPaths.contains(folderpaths)) {
                    videoPaths.add(folderpaths);
                    fold.setPath(folderpaths);
                    fold.setName(folder);
                    videoFolders.add(fold);
                }
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fetchData();
    }

    private void fetchData() {
        FolderAdapter folderAdapter = new FolderAdapter(videoFolders,this);
        rvGalleries.setAdapter(folderAdapter);
        rvGalleries.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    public void onFolderClick(int pos) {
       Intent intent = new Intent(OpenVideoFolders.this,ChooseVideoActivity.class);
       intent.putExtra(ChooseVideoActivity.KEY_GET_VIDEO_FOLDER_PATH,videoFolders.get(pos).getPath());
       startActivity(intent);
    }
}