package com.example.demoimagesconveter.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.model.ModelFrame;

import java.util.ArrayList;

public class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.ViewHolder> {
    ArrayList<ModelFrame> images;
    onImageGalleryClickListener clickListener;

    public ImageGalleryAdapter(ArrayList<ModelFrame> images, onImageGalleryClickListener clickListener) {
        this.images = images;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery_image,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(position);
//        holder.cbDelete.setVisibility(mAreCheckboxesVisible ? View.VISIBLE : View.GONE);
//        holder.itemView.setOnLongClickListener(view -> {
//            mAreCheckboxesVisible = true;
//            notifyDataSetChanged();
//            return true;
//        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private int pos;
        ImageView imvGallery;
        TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvGallery = itemView.findViewById(R.id.imv_item_gallery_image);
            tvTitle = itemView.findViewById(R.id.tv_item_gallety_title);
            //cbDelete = itemView.findViewById(R.id.cb_item_gallery);
            itemView.setOnLongClickListener(view -> {
                //cbDelete.setVisibility(View.VISIBLE);
                return true;
            });
            itemView.setOnClickListener(view -> clickListener.onItemClick(pos));
        }

        public void bindView(int pos){
            this.pos = pos;
            Bitmap bitmap = BitmapFactory.decodeFile(images.get(pos).getPath());
            Glide.with(itemView.getContext()).load(bitmap).into(imvGallery);
            tvTitle.setText(images.get(pos).getTitle());
        }

    }

    public interface onImageGalleryClickListener{
        void onItemLongClick(int pos);
        void onItemClick(int pos);
    }
}
