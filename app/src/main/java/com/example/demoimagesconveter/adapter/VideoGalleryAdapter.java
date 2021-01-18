package com.example.demoimagesconveter.adapter;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.model.ModelVideo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class VideoGalleryAdapter extends RecyclerView.Adapter<VideoGalleryAdapter.ViewHolder> {
    ArrayList<ModelVideo> videos;
    onVideoClickListener clickListener;

    public VideoGalleryAdapter(ArrayList<ModelVideo> videos, onVideoClickListener clickListener) {
        this.videos = videos;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery_video,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        int pos;
        TextView tvTitle,tvCapacity,tvDate;
        ImageView imvThumb,imvMenu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_gallery_video_title);
            imvThumb = itemView.findViewById(R.id.imv_item_gallery_video_thumb);
            imvMenu = itemView.findViewById(R.id.imv_gallery_video_menu);
            tvCapacity = itemView.findViewById(R.id.tv_item_gallery_video_capacity);
            tvDate = itemView.findViewById(R.id.tv_item_gallery_video_date);
            imvMenu.setOnClickListener(view -> clickListener.onMenuClick(pos,view));
        }

        public void bindView(int pos){
            this.pos = pos;
            tvTitle.setText(videos.get(pos).getTitle());
            Bitmap thumb = ThumbnailUtils.createVideoThumbnail(String.valueOf((videos.get(pos)).getPath()), MediaStore.Images.Thumbnails.MINI_KIND);
            Glide.with(itemView.getContext()).load(thumb).into(imvThumb);
            File file = new File(videos.get(pos).getPath());
            String capacity ="Capacity: " + file.length() / 1000 + " Kb";
            tvCapacity.setText(capacity);
            Date lastModDate = new Date(file.lastModified());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String date = sdf.format(lastModDate.getTime());
            tvDate.setText(date);
        }
    }

    public interface onVideoClickListener{
        void onMenuClick(int pos,View view);
    }
}
