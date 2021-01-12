package com.example.demoimagesconveter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.model.ModelAudio;

import java.util.ArrayList;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.ViewHolder> {

    ArrayList<ModelAudio> audios;
    onItemClickListener clickListener;

    public AudioAdapter(ArrayList<ModelAudio> audios, onItemClickListener clickListener) {
        this.audios = audios;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_audio,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return audios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        int pos;
        TextView tvTitle,tvSize,tvTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_audio_title);
            tvSize = itemView.findViewById(R.id.tv_item_audio_size);
            tvTime = itemView.findViewById(R.id.tv_item_audio_time);
            itemView.setOnClickListener(view -> clickListener.onAudioClickListener(pos));
        }
        public void bindView(int pos){
            this.pos = pos;
            tvTitle.setText(audios.get(pos).getTitle());
            String size = audios.get(pos).getSize()/1024 + " Kb";
            tvSize.setText(size);
            tvTime.setText(MilliSecondsToTimer(""+audios.get(pos).getDuration()));
        }

        public String MilliSecondsToTimer(String duration){
            long millSec = Long.parseLong(duration);
            String finalTimerString;
            String hoursString = "";
            String secondString;
            String minuteString;
            int seconds = (int) (millSec / 1000) % 60 ;
            int minutes = (int) ((millSec / (1000*60)) % 60);
            int hours   = (int) ((millSec / (1000*60*60)) % 24);
            if (hours > 0) {
                hoursString = hours + ":";
            }
            if (seconds < 10) {
                secondString = "0" + seconds;
            } else secondString = "" + seconds;
            if (minutes < 10) {
                minuteString = "0" + minutes;
            } else minuteString = "" + minutes;
            finalTimerString = hoursString + minuteString + ":" + secondString;
            return finalTimerString;
        }
    }

    public interface onItemClickListener{
         void onAudioClickListener(int pos);
    }
}
