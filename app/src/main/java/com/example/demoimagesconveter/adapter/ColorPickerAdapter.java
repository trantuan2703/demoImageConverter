package com.example.demoimagesconveter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoimagesconveter.R;

import java.util.ArrayList;

public class ColorPickerAdapter extends RecyclerView.Adapter<ColorPickerAdapter.ViewHolder> {
    private final ArrayList<Integer> colorsList;
    onColorPicker onColorPicker;

    public ColorPickerAdapter(Context context, ColorPickerAdapter.onColorPicker onColorPicker) {
        this.colorsList = getDefaultColors(context);
        this.onColorPicker = onColorPicker;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color_picker,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindview(position);
    }

    @Override
    public int getItemCount() {
        return colorsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        int pos;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.color_picker_view);
        }
        public void bindview(int pos){
            this.pos = pos;
            view.setBackgroundColor(colorsList.get(pos));
            view.setOnClickListener(v -> onColorPicker.onColorPickerListener(colorsList.get(getAdapterPosition())));
        }
    }

    public static ArrayList<Integer> getDefaultColors(Context context) {
        ArrayList<Integer> colorPickerColors = new ArrayList<>();
        colorPickerColors.add(ContextCompat.getColor(context, R.color.white));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.blue_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.brown_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.green_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.orange_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.red_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.black));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.red_orange_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.sky_blue_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.violet_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.white));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.yellow_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.yellow_green_color_picker));
        return colorPickerColors;
    }

    public interface onColorPicker{
        void onColorPickerListener(int pos);
    }
}
