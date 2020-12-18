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

public class SelectedFrameAdapter extends RecyclerView.Adapter<SelectedFrameAdapter.VierwHolder> {

    ArrayList<ModelFrame> frames;
    onSelectedFrameClickListner clickListner;

    public SelectedFrameAdapter(ArrayList<ModelFrame> frames, onSelectedFrameClickListner clickListner) {
        this.frames = frames;
        this.clickListner = clickListner;
    }

    @NonNull
    @Override
    public VierwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_frame,parent,false);
        return new VierwHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VierwHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return frames.size();
    }

    public class VierwHolder extends RecyclerView.ViewHolder {
        ImageView imvFrame,imvDelete;
        int pos;
        public VierwHolder(@NonNull View itemView) {
            super(itemView);
            imvFrame = itemView.findViewById(R.id.imv_item_selected_frame);
            imvDelete = itemView.findViewById(R.id.imv_item_selected_frame_delete);
            imvDelete.setOnClickListener(view -> clickListner.onDeleteClick(pos));
        }

        public void bindView(int pos){
            this.pos = pos;
            Glide.with(itemView.getContext()).load(frames.get(pos).getFrame()).into(imvFrame);
        }
    }
    public interface onSelectedFrameClickListner{
        void onDeleteClick(int pos);
    }
}
