package com.example.demoimagesconveter.view.Capture;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.adapter.FolderAdapter;
import com.example.demoimagesconveter.common.BaseActivity;
import com.example.demoimagesconveter.model.ModelFolder;

import java.util.ArrayList;

public class OpenVideoFolders extends BaseActivity implements FolderAdapter.onFolderClickListener {

    public static final int REQUEST_CODE_OPEN_GALLERY = 5;
    ImageView imvBack;
    RecyclerView rvGalleries;
    private final ArrayList<ModelFolder> videoFolders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_video_gallery);
        init();
        registerevent();
    }

    private void registerevent() {
        imvBack.setOnClickListener(view -> finish());
    }

    private void init() {
        rvGalleries = findViewById(R.id.rv_galleries);
        imvBack = findViewById(R.id.imv_open_video_gallery_back);
        String[] permissionOpenGallery = {Manifest.permission.READ_EXTERNAL_STORAGE};
        if (checkPermission(OpenVideoFolders.this, permissionOpenGallery)) {
            getAllFolder();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissionOpenGallery, REQUEST_CODE_OPEN_GALLERY);
            }
        }
    }

    private boolean checkPermission(Context context, String[] permissions) {
        if (context != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
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
                ModelFolder fold = new ModelFolder();
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
        FolderAdapter folderAdapter = new FolderAdapter(videoFolders, this);
        rvGalleries.setAdapter(folderAdapter);
        rvGalleries.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    public void onFolderClick(int pos) {
        Intent intent = new Intent(OpenVideoFolders.this, ChooseVideoActivity.class);
        intent.putExtra(ChooseVideoActivity.KEY_GET_VIDEO_FOLDER_PATH, videoFolders.get(pos).getPath());
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_OPEN_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showtoast("Permission granted");
                getAllFolder();
            } else {
                showtoast("Permission denied");
            }
        } else {
            showtoast("Permission denied");
        }
    }
}