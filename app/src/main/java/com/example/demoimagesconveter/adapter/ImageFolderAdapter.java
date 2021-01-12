package com.example.demoimagesconveter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.model.ModelFolder;

import java.io.File;
import java.util.ArrayList;

public class ImageFolderAdapter extends RecyclerView.Adapter<ImageFolderAdapter.ViewHolder> {

    private final ArrayList<ModelFolder> ModelFolders;
    private final onImageFolderClickListener clickListener;

    public ImageFolderAdapter(ArrayList<ModelFolder> modelFolders, onImageFolderClickListener clickListener) {
        ModelFolders = modelFolders;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ImageFolderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_folder,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageFolderAdapter.ViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return ModelFolders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        int pos;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tv_image_folder_name);
            itemView.setOnClickListener(view -> clickListener.onClickListener(pos));
        }
        public void bindView(int pos){
            this.pos=pos;
            File path = new File(ModelFolders.get(pos).getPath());
            File[] files = path.listFiles();
            ArrayList<File> imagesFiles = new ArrayList<>();
            for (File file: files){
                if (file.getName().endsWith(".jpeg")||file.getName().endsWith(".png")||file.getName().endsWith(".jpg")){
                    imagesFiles.add(file);
                }
            }
            String name= ModelFolders.get(pos).getName()+"("+imagesFiles.size()+")";
            tvName.setText(name);
        }
    }
    public interface onImageFolderClickListener{
         void onClickListener(int pos);
    }
}
