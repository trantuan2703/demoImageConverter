package com.example.demoimagesconveter.view.SldieShow;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.adapter.FrameAdapter;
import com.example.demoimagesconveter.adapter.ImageFolderAdapter;
import com.example.demoimagesconveter.adapter.SelectedFrameAdapter;
import com.example.demoimagesconveter.common.BaseActivity;
import com.example.demoimagesconveter.model.ModelFolder;
import com.example.demoimagesconveter.model.ModelFrame;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class PrepareSlideshowActivity extends BaseActivity implements ImageFolderAdapter.onImageFolderClickListener,
                                                                FrameAdapter.onFrameClickListener,
                                                                 SelectedFrameAdapter.onSelectedFrameClickListner{

    public static final int REQUEST_CODE_OPEN_IMAGES_FOLDER = 47;
    ImageView imvBack,imvAdd;
    private ArrayList<ModelFolder> folders = new ArrayList<>();
    private final ArrayList<ModelFrame> selectedImages = new ArrayList<>();
    ArrayList<ModelFrame> images = new ArrayList<>();
    RecyclerView rvImagesFolder,rvImages,rvSelectedImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_slideshow);
        init();
        registerevent();
    }

    private void registerevent() {
        imvBack.setOnClickListener(view -> finish());

        imvAdd.setOnClickListener(view -> {
            Intent intent = new Intent(PrepareSlideshowActivity.this,SlideshowActivity.class);
            ArrayList<String> paths = new ArrayList<>();
            for (int i = 0; i < selectedImages.size(); i++) {
                paths.add(selectedImages.get(i).getPath());
            }
            intent.putExtra(SlideshowActivity.KEY_GET_IMAGES_TO_CREATE_SLIDE_SHOW,paths);
            startActivity(intent);
        });
    }

    private void init() {
        imvBack = findViewById(R.id.imv_prepare_slideshow_back);
        rvImagesFolder = findViewById(R.id.rv_slideshow_images_folders);
        rvImages = findViewById(R.id.rv_slideshow_images_from_folder);
        rvSelectedImages = findViewById(R.id.rv_slideshow_selected_images);
        imvAdd = findViewById(R.id.imv_prepare_slideshow_add);
        String[] permissionOpenGallery = {Manifest.permission.READ_EXTERNAL_STORAGE};
        if (checkPermission(PrepareSlideshowActivity.this, permissionOpenGallery)) {
            getFile();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissionOpenGallery, REQUEST_CODE_OPEN_IMAGES_FOLDER);
            }
        }
        getFile();
        fetchView();
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

    private void fetchView() {
        ImageFolderAdapter folderAdapter = new ImageFolderAdapter(folders,this);
        rvImagesFolder.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        rvImagesFolder.setAdapter(folderAdapter);
        folderAdapter.notifyDataSetChanged();
    }

    public void getFile() {
        ArrayList<ModelFolder> imagesFolders = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String imagePath = cursor.getString(cursor.getColumnIndex(projection[0]));
                imagesFolders.add(CreateFolder(imagePath));
            }
            cursor.close();
        }
        Map<String, ModelFolder> cleanMap = new LinkedHashMap<>();
        for (int i = 0; i < imagesFolders.size(); i++) {
            cleanMap.put(imagesFolders.get(i).getName(), imagesFolders.get(i));
        }
        folders = new ArrayList<>(cleanMap.values());
        for (int i = 0; i < folders.size(); i++) {
            Log.d("GET_IMAGE_FOLDER","name: "+folders.get(i).getName()+"-path: "+folders.get(i).getPath());
        }
    }

    private ModelFolder CreateFolder(String imagePath) {
        ArrayList<String> prefixList = new ArrayList<>();
        for (int i = 0; i < imagePath.length(); i++) {
            if (String.valueOf(imagePath.charAt(i)).equals("/")) {
                prefixList.add(String.valueOf(i));
            }
        }
        String name = imagePath.substring(Integer.parseInt(prefixList.get(3))+1,Integer.parseInt(prefixList.get(4)));
        String path = imagePath.substring(0,Integer.parseInt(prefixList.get(prefixList.size()-1)));
        ModelFolder folder = new ModelFolder();
        folder.setPath(path);
        folder.setName(name);
        return folder;
    }

    @Override
    public void onClickListener(int pos) {
        fetchImageView(folders.get(pos).getPath());
    }

    private void fetchImageView(String folderPath) {
        images.clear();
        File dir = new File(folderPath);
        File[] files = dir.listFiles();
        if (files!=null){
            for (File file: files){
                if (file.getName().endsWith(".jpeg")||file.getName().endsWith(".png")||file.getName().endsWith(".jpg")){
                    ModelFrame frame = new ModelFrame();
                    frame.setPath(file.getAbsolutePath());
                    frame.setFrame(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    images.add(frame);
                }
            }
        }
        FrameAdapter frameAdapter = new FrameAdapter(images,this);
        rvImages.setAdapter(frameAdapter);
        rvImages.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        frameAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFrameClick(int pos){
        selectedImages.add(images.get(pos));
        fetchSelectedImage();
    }

    private void fetchSelectedImage() {
        SelectedFrameAdapter frameAdapter = new SelectedFrameAdapter(selectedImages,this);
        rvSelectedImages.setAdapter(frameAdapter);
        rvSelectedImages.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rvSelectedImages.scrollToPosition(selectedImages.size()-1);
        frameAdapter.notifyDataSetChanged();

        imvAdd.setVisibility(View.VISIBLE);
        if (selectedImages.size()==0){
            imvAdd.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDeleteClick(int pos) {
        selectedImages.remove(selectedImages.get(pos));
        fetchSelectedImage();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_OPEN_IMAGES_FOLDER) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showtoast("Permission granted");
                getFile();
            } else {
                showtoast("Permission denied");
            }
        } else {
            showtoast("Permission denied");
        }
    }
}