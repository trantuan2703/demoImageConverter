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

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private final ArrayList<ModelVideo> videos;
    private final onVideoClickListener clickListener;

    public VideoAdapter(ArrayList<ModelVideo> videos, onVideoClickListener clickListener) {
        this.videos = videos;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video,parent,false);
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
        ImageView imvThumb;
        TextView tvTitle,tvDuration;
        int pos;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvThumb=itemView.findViewById(R.id.imv_item_video_thumb);
            tvTitle=itemView.findViewById(R.id.tv_item_video_title);
            tvDuration=itemView.findViewById(R.id.tv_item_video_duration);
            itemView.setOnClickListener(view -> clickListener.onClick(pos));
        }

        public void bindView(int pos){
            this.pos=pos;
            tvTitle.setText(videos.get(pos).getTitle());
            Bitmap thumb = ThumbnailUtils.createVideoThumbnail(String.valueOf((videos.get(pos)).getPath()), MediaStore.Images.Thumbnails.MINI_KIND);
            Glide.with(itemView.getContext()).load(thumb).into(imvThumb);
            tvDuration.setText(videos.get(pos).getShowDuration());
        }
    }
    public interface onVideoClickListener{
        void onClick(int pos);
    }
}
