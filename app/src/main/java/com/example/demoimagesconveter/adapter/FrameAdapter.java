package com.example.demoimagesconveter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.model.ModelFrame;

import java.util.ArrayList;

public class FrameAdapter extends RecyclerView.Adapter<FrameAdapter.ViewHolder> {

    ArrayList<ModelFrame> frames;
    onFrameClickListener clickListener;

    public FrameAdapter(ArrayList<ModelFrame> frames, onFrameClickListener clickListener) {
        this.frames = frames;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_frame,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindview(position);
    }

    @Override
    public int getItemCount() {
        return frames.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imvFrame;
        int pos;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvFrame=itemView.findViewById(R.id.imv_item_frame);
            itemView.setOnClickListener(view -> clickListener.onFrameClick(pos));
        }
        public void bindview(int pos) {
            this.pos = pos;
            Glide.with(itemView.getContext()).load(frames.get(pos).getFrame()).into(imvFrame);
        }
    }
    public interface onFrameClickListener{
        void onFrameClick(int pos);
    }
}
