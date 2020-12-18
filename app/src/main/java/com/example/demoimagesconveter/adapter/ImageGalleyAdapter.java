package com.example.demoimagesconveter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.model.ModelImage;

import java.util.ArrayList;

public class ImageGalleyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    ArrayList<ModelImage> images;

    public ImageGalleyAdapter(ArrayList<ModelImage> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_ITEM) {
            return new VHItem(layoutInflater.inflate(R.layout.item_date_image, parent, false));
        } else if (viewType == TYPE_HEADER) {
            return new VHHeader(layoutInflater.inflate(R.layout.item_image, parent, false));
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }


    @Override
    public int getItemCount() {
        return images.size();
    }
    static class VHItem extends RecyclerView.ViewHolder {
        TextView title;
        public VHItem(View itemView) {
            super(itemView);
        }
    }
    static class VHHeader extends RecyclerView.ViewHolder {
        ImageView mImage;
        public VHHeader(View itemView) {
            super(itemView);
        }
    }
}
