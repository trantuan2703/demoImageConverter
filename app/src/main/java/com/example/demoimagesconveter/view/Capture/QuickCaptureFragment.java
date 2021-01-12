package com.example.demoimagesconveter.view.Capture;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.adapter.SelectedFrameAdapter;
import com.example.demoimagesconveter.common.BaseFragment;
import com.example.demoimagesconveter.model.ModelFrame;
import com.example.demoimagesconveter.model.ModelVideo;
import com.example.demoimagesconveter.view.Setting.SettingActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class QuickCaptureFragment extends BaseFragment implements SelectedFrameAdapter.onSelectedFrameClickListner{
    ImageView imvPlay;
    TextView tvTitle, tvEndTime, tvCurrentTime;
    public VideoView videoViewQuickCapture;
    SeekBar sbVideo;
    String type, quality;
    FloatingActionButton fabCap, fabSave;
    RecyclerView rvFrames;
    SelectedFrameAdapter frameAdapter;
    MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
    ArrayList<ModelFrame> frames = new ArrayList<>();
    boolean isPlayVideo = false;
    ModelVideo modelVideo;
    public QuickCaptureFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragments
        return inflater.inflate(R.layout.fragment_quick_capture, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        fetchview();
        registerevent();
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d("STATUS_QUICK_FRAGMET","pause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("STATUS_QUICK_FRAGMET","resume");
    }

    @SuppressLint("ClickableViewAccessibility")
    private void registerevent() {
        videoViewQuickCapture.setOnTouchListener((view, motionEvent) -> {
            startVideo(!videoViewQuickCapture.isPlaying());
            Log.e("TAP", "from video " + videoViewQuickCapture.getCurrentPosition());
            return false;
        });
        if (modelVideo != null)
            sbVideo.setMax(Integer.parseInt(modelVideo.getDuration()));
        startVideo(true);
        sbVideo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    tvCurrentTime.setText(MilliSecondsToTimer(String.valueOf(i)));
                    videoViewQuickCapture.seekTo(i);
                    if (isPlayVideo){
                        videoViewQuickCapture.start();
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        fabCap.setOnClickListener(view -> {
            int currentDuration = videoViewQuickCapture.getCurrentPosition();
            metadataRetriever.setDataSource(modelVideo.getPath());
            Bitmap bmCurrnetFrame = metadataRetriever.getFrameAtTime((long) currentDuration * 1000);
            if (bmCurrnetFrame != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                String currentDateAndTime = sdf.format(new Date());
                String imageTitle = modelVideo.getTitle().substring(0, modelVideo.getTitle().length() - 4) + "_" + currentDateAndTime;
                ModelFrame frame = new ModelFrame(imageTitle, currentDateAndTime, bmCurrnetFrame);
                frames.add(frame);
                fetchCapturedFrame();
            } else {
                showToast("Can't capture image");
            }
        });

        fabSave.setOnClickListener(view -> {
            if (frames.isEmpty()) {
                showToast("Nothing to save");
            } else {
                for (int i = 0; i < frames.size(); i++) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    saveImage(frames.get(i).getFrame(), frames.get(i).getTitle());
                }
                showToast("Save Successfully");
                frames.clear();
                frameAdapter.notifyDataSetChanged();
            }
        });
    }

    void startVideo(boolean isStart){
        isPlayVideo = isStart;
        if (isStart){
            imvPlay.setVisibility(View.INVISIBLE);
            videoViewQuickCapture.start();
            sbVideo.postDelayed(onEverySecond, 1000);
        }else{
            videoViewQuickCapture.pause();
            imvPlay.setVisibility(View.VISIBLE);
            sbVideo.removeCallbacks(onEverySecond);//important line
        }
    }

    public void saveImage(Bitmap bitmap, String name) {
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File imageDir = new File(root,"/DemoImagesConverter");
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }
        String fname = name+getEndType(type);
        File file = new File(imageDir,fname);
           try {
               FileOutputStream out = new FileOutputStream(file);
               if (type.equals("JPEG")){
                   bitmap.compress(Bitmap.CompressFormat.JPEG, getQuality(quality), out);
               }else{
                   bitmap.compress(Bitmap.CompressFormat.PNG,getQuality(quality),out);
               }
               out.flush();
               out.close();
           } catch (Exception e) {
               e.printStackTrace();
           }

        MediaScannerConnection.scanFile(getContext(), new String[] { file.toString() }, null,
                (path, uri) -> {
                    Log.i("SAVE", "Scanned " + path + ":");
                    Log.i("SAVE", "-> uri=" + uri);
                });
    }
    private void fetchCapturedFrame() {
        frameAdapter = new SelectedFrameAdapter(frames,this);
        rvFrames.setAdapter(frameAdapter);
        rvFrames.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvFrames.scrollToPosition(frames.size()-1);
        frameAdapter.notifyDataSetChanged();
    }

    private final Runnable onEverySecond = new Runnable() {
        @Override
        public void run() {
            if (sbVideo != null) {
                sbVideo.setProgress(videoViewQuickCapture.getCurrentPosition());
            }
            if (isPlayVideo) {
                sbVideo.postDelayed(onEverySecond, 1000);
            }
        }
    };

    private void fetchview() {
        modelVideo = getVideoFromActivity();
        tvTitle.setText(modelVideo.getTitle().substring(0, modelVideo.getTitle().length() - 4));
        tvEndTime.setText(MilliSecondsToTimer(modelVideo.getDuration()));
        videoViewQuickCapture.setVideoPath(modelVideo.getPath());
    }

    private void init(View view) {
        tvEndTime = view.findViewById(R.id.tv_quick_capture_end);
        tvTitle = view.findViewById(R.id.tv_capture_image_title);
        imvPlay = view.findViewById(R.id.imv_quick_capture_play);
        videoViewQuickCapture = view.findViewById(R.id.vv_quick_captured_video);
        sbVideo = view.findViewById(R.id.sb_quick_capture);
        fabCap = view.findViewById(R.id.fab_quick_capture);
        rvFrames = view.findViewById(R.id.rv_quick_capture_images);
        tvCurrentTime = view.findViewById(R.id.tv_quick_cap_current_time);
        fabSave = view.findViewById(R.id.fab_quick_save);

        if (getContext() != null) {
            SharedPreferences pref = getContext().getSharedPreferences(SettingActivity.KEY_SHARED_PREFERENCE, Context.MODE_PRIVATE);
            type = pref.getString(SettingActivity.KEY_SHARED_PREFERENCE_TYPE_FORMAT, "JPEG");
            quality = pref.getString(SettingActivity.KEY_SHARED_PREFERENCE_QUALITY, "High");
        }
        Log.d("READ_YOUR_SETTING", "type: " + type + "- quality: " + quality);
    }


    @Override
    public void onDeleteClick(int pos) {
        frames.remove(frames.get(pos));
        fetchCapturedFrame();
    }
}