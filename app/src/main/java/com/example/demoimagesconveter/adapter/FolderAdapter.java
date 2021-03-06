package com.example.demoimagesconveter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.model.ModelFolder;

import java.util.ArrayList;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {

    private final ArrayList<ModelFolder> ModelFolders;
    private final onFolderClickListener clickListener;

    public FolderAdapter(ArrayList<ModelFolder> ModelFolders, onFolderClickListener clickListener) {
        this.ModelFolders = ModelFolders;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_folder,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return ModelFolders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        int pos;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tv_folder_name);
            itemView.setOnClickListener(view -> clickListener.onFolderClick(pos));
        }
        public void bindView(int pos){
            this.pos=pos;
            tvName.setText(ModelFolders.get(pos).getName());
        }
    }

    public interface onFolderClickListener{
        void onFolderClick(int pos);
    }
}
