package com.example.demoimagesconveter.view.SldieShow;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.adapter.AudioAdapter;
import com.example.demoimagesconveter.common.BaseActivity;
import com.example.demoimagesconveter.model.ModelAudio;

import java.util.ArrayList;

public class AddAudioActivity extends BaseActivity implements AudioAdapter.onItemClickListener{
    public static final String KEY_SEND_AUDIO = "KEY_SEND_AUDIO";
    public static final String KEY_SEND_SECOND_PER_IMAGE = "KEY_SEND_SECOND_PER_IMAGE";
    ImageView imvBack;
    ArrayList<ModelAudio> audios = new ArrayList<>();
    RecyclerView rvAudios;
    AudioAdapter audioAdapter;
    MediaPlayer mediaPlayer;
    TextView tvSsLength,tvMin,tvMax;
    private String track;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_audio);
        init();
        registerevent();
    }

    private void registerevent() {
        imvBack.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.putExtra(KEY_SEND_AUDIO, track);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    private void init() {
        imvBack = findViewById(R.id.imv_add_audio_back);
        rvAudios = findViewById(R.id.rv_add_audio);
        tvSsLength = findViewById(R.id.tv_add_audio_slideshow_lenghth);
        tvMin = findViewById(R.id.tv_add_audio_min_value);
        tvMax = findViewById(R.id.tv_add_audio_max_value);
        mediaPlayer = new MediaPlayer();
        getAllAudioFiles();
        fetchView();
        for (int i = 0; i < audios.size(); i++) {
            Log.d("TEST", audios.get(i).getTitle()+"-"+ audios.get(i).getPath()+"-"+ audios.get(i).getDuration());
        }
        int slideshowLength = getIntent().getIntExtra(KEY_SEND_SECOND_PER_IMAGE, 2) + 1;
        String length = "Your slide show length is: "+ slideshowLength +" seconds";
        tvSsLength.setText(length);
    }

    private void fetchView() {
        audioAdapter = new AudioAdapter(audios,this);
        rvAudios.setAdapter(audioAdapter);
        rvAudios.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }

    private void getAllAudioFiles() {
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if (musicCursor != null && musicCursor.moveToFirst()) {
            int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int pathColumn = musicCursor.getColumnIndex( MediaStore.Audio.Media.DATA);
            int sizeColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.SIZE);
            int durationColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            do {
                long id = musicCursor.getLong(idColumn);
                String title = musicCursor.getString(titleColumn);
                String artist = musicCursor.getString(artistColumn);
                String path = musicCursor.getString(pathColumn);
                int size = musicCursor.getInt(sizeColumn);
                int duration = musicCursor.getInt(durationColumn);
                ModelAudio audio = new ModelAudio();
                audio.setPath(path);
                audio.setArtist(artist);
                audio.setId(id);
                audio.setTitle(title);
                audio.setSize(size);
                audio.setDuration(duration);
                audios.add(audio);
            } while (musicCursor.moveToNext());
        }
    }

    @Override
    public void onAudioClickListener(int pos) {
        ModelAudio audio = audios.get(pos);
        track = audio.getPath();
    }

}