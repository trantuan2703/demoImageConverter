package com.example.demoimagesconveter.view.Gallery;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.adapter.ImageGalleryAdapter;
import com.example.demoimagesconveter.common.BaseFragment;
import com.example.demoimagesconveter.model.ModelFrame;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class GalleryImagesFragment extends BaseFragment implements ImageGalleryAdapter.onImageGalleryClickListener {
    ArrayList<ModelFrame> images = new ArrayList<>();
    RecyclerView rvImages;
    public GalleryImagesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery_images, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getImages();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        fetchView();
    }

    private void fetchView() {
        ImageGalleryAdapter imageGalleryAdapter = new ImageGalleryAdapter(images,this);
        rvImages.setAdapter(imageGalleryAdapter);
        rvImages.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    private void init(View view) {
        getImages();
        rvImages = view.findViewById(R.id.rv_gallery_iamges);
    }

    private void getImages() {
        images.clear();
        File imagesFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/DemoImagesConverter");
        File[] files = imagesFile.listFiles();
        if (files!=null){
            for (File file : files){
                if (file.getName().endsWith(".jpeg")||file.getName().endsWith(".png")){
                    String path = file.getAbsolutePath();
                    Date lastModDate = new Date(file.lastModified());
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String date = sdf.format(lastModDate.getTime());
                    String title = file.getName();
                    ModelFrame image = new ModelFrame();
                    image.setPath(path);
                    image.setDate(date);
                    image.setTitle(title);
                    image.setSelected(false);
                    images.add(image);
                }
            }
        }
    }

    @Override
    public void onItemLongClick(int pos) {

    }

    @Override
    public void onItemClick(int pos) {
        Intent intent = new Intent(getContext(),EditImageActivity.class);
        intent.putExtra(EditImageActivity.KEY_GET_IMAGE_PATH,images.get(pos).getPath());
        startActivity(intent);
    }
}