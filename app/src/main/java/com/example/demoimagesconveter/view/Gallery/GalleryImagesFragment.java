package com.example.demoimagesconveter.view.Gallery;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.common.BaseFragment;
import com.example.demoimagesconveter.model.ModelImage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class GalleryImagesFragment extends BaseFragment {
    ArrayList<ModelImage> images = new ArrayList<>();
    public GalleryImagesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery_images, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        fetchView();
    }

    private void fetchView() {

    }

    private void init(View view) {
        getImages();
    }

    private void getImages() {
        File imagesFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/DemoImagesConverter");
        File[] files = imagesFile.listFiles();
        if (files!=null){
            for (File file : files){
                if (file.getName().endsWith(".jpeg")||file.getName().endsWith(".png")){
                    String path = file.getAbsolutePath();
                    Date lastModDate = new Date(file.lastModified());
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String date = sdf.format(lastModDate.getTime());
                    ModelImage image = new ModelImage(date,path);
                    images.add(image);
                }
            }
            showToast(""+images.size());
        }
    }
}