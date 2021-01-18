package com.example.demoimagesconveter.view.Gallery;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.adapter.VideoGalleryAdapter;
import com.example.demoimagesconveter.common.BaseFragment;
import com.example.demoimagesconveter.model.ModelVideo;

import java.io.File;
import java.util.ArrayList;

public class GalleryVideosFragment extends BaseFragment implements VideoGalleryAdapter.onVideoClickListener{
    ArrayList<ModelVideo> videos = new ArrayList<>();
    RecyclerView rvVideos;
    VideoGalleryAdapter videoAdapter;
    public GalleryVideosFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery_videos, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        fetchView();
    }

    private void fetchView() {
        videoAdapter = new VideoGalleryAdapter(videos,this);
        rvVideos.setAdapter(videoAdapter);
        rvVideos.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        videoAdapter.notifyDataSetChanged();
    }

    private void init(View view) {
        getVideos();
        rvVideos = view.findViewById(R.id.rv_gallery_videos);
    }

    private void getVideos() {
        videos.clear();
        File imagesFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/DemoImagesConverter");
        File[] files = imagesFile.listFiles();
        if (files!=null){
            for (File file : files){
                if (file.getName().endsWith(".mp4")||file.getName().endsWith(".wav")){
                    ModelVideo modelVideo = new ModelVideo();
                    modelVideo.setTitle(file.getName());
                    modelVideo.setPath(file.getAbsolutePath());
                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    retriever.setDataSource(getContext(), Uri.fromFile(new File(modelVideo.getPath())));
                    String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                    modelVideo.setDuration(time);
                    modelVideo.setShowDuration(MilliSecondsToTimer(time));
                    videos.add(modelVideo);
                }
            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onMenuClick(int pos,View view) {
        String videoPath = videos.get(pos).getPath();
        PopupMenu popupMenu = new PopupMenu(getContext(),view);
        popupMenu.inflate(R.menu.menu_gallery_video_option);
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.menu_edit_image_info:
                    showDialogInfo(videoPath);
                    break;
                case R.id.menu_gallery_menu_video_share:
                    shareVideo(videoPath);
                    break;
                case R.id.menu_gallery_menu_video_delete:
                    deleteVideo(videoPath,pos);
                    break;
                default:
                    break;
            }
            return true;
        });
        popupMenu.show();
    }

    private void deleteVideo(String path,int position) {
        File file = new File(path);
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_delete_this);
        dialog.show();
        ImageView imvAccept = dialog.findViewById(R.id.imv_dialog_delete_accept);
        ImageView imvDecline = dialog.findViewById(R.id.imv_dialog_delete_decline);
        imvDecline.setOnClickListener(view -> dialog.dismiss());
        imvAccept.setOnClickListener(view -> {
            boolean deleteImage = file.delete();
            if (deleteImage){
                showToast("Delete Successfully");
                videos.remove(videos.get(position));
                getVideos();
                videoAdapter.notifyDataSetChanged();
            }else{
                showToast("Unsuccessfully");
            }
            dialog.dismiss();
        });
    }

    private void showDialogInfo(String path) {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_image_information);
        dialog.show();
        String dir = "Directory: "+ path;
        TextView tvDir = dialog.findViewById(R.id.tv_dialog_info_dir);
        tvDir.setText(dir);
        File file = new File(path);
        String capacity ="Capacity: " + file.length() / 1000 + " Kb";
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(new File(path).getAbsolutePath(), options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        TextView tvCapacity = dialog.findViewById(R.id.tv_dialog_info_capacity);
        tvCapacity.setText(capacity);
        TextView tvSize = dialog.findViewById(R.id.tv_dialog_info_size);
        String size = "Size: "+imageHeight+" x "+imageWidth;
        tvSize.setText(size);
    }

    private void shareVideo(String path) {
        Intent shareIntent = new Intent();
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(path)));
        shareIntent.setType("video/mp4");
        startActivity(Intent.createChooser(shareIntent, "Checkout my video"));

    }
}