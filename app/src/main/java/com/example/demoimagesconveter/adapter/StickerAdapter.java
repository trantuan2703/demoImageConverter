package com.example.demoimagesconveter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoimagesconveter.R;

import java.util.ArrayList;

public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {
    ArrayList<String> emojisList;
    onStickerClickListener clickListener;

    public StickerAdapter(ArrayList<String> emojisList, onStickerClickListener clickListener) {
        this.emojisList = emojisList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emoji, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return emojisList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        int pos;
        TextView tvEmoji;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmoji = itemView.findViewById(R.id.tv_item_Emoji);
            itemView.setOnClickListener(v -> clickListener.onClick(pos));
        }
        public void bindView(int pos){
            this.pos = pos;
            tvEmoji.setText(emojisList.get(pos));
        }
    }

    public interface onStickerClickListener{
        void onClick(int pos);
    }
}
