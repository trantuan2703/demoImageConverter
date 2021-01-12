package com.example.demoimagesconveter.view.SldieShow;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.common.BaseActivity;
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SlideshowActivity extends BaseActivity {
    public static final String KEY_GET_IMAGES_TO_CREATE_SLIDE_SHOW="KEY_GET_IMAGES_TO_CREATE_SLIDE_SHOW";
    public static final int KEY_GET_AUDIO = 120;
    ImageView imvBack;
    TextView tvAdd,tvSlidePerSec,tvAddAudio;
    FloatingActionButton fabCrete;
    VideoView vvSlideshow;
    private int secPerImage = 2;
    ArrayList<String> paths = new ArrayList<>();
    FFmpeg ffmpeg;
    String appRoot = Environment.getExternalStorageDirectory().getAbsolutePath()+"/DemoImagesConverter";
    String audioPath;
    File preAudioInputdest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);
        init();
        loadFFMPEG();
        registerevent();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void registerevent() {
        imvBack.setOnClickListener(view -> onBackPressed());
        tvAdd.setOnClickListener(view -> onBackPressed());
        tvSlidePerSec.setOnClickListener(view -> openSlidePerSecond());
        fabCrete.setOnClickListener(view -> createVideoAgain());
        tvAddAudio.setOnClickListener(view -> {
            Intent intent = new Intent(SlideshowActivity.this,AddAudioActivity.class);
            intent.putExtra(AddAudioActivity.KEY_SEND_SECOND_PER_IMAGE,secPerImage*paths.size());
            startActivityForResult(intent,KEY_GET_AUDIO);
        });
    }

    private void createVideoAgain() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());
        File dest = new File(appRoot, "slideshow_"+currentDateAndTime+".mp4");
        String textFile = "";
        try {
            textFile = getTextFile2().getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        preAudioInputdest = new File(appRoot,"preAudioInputFiles.mp3");
        String[] cmd = new String[]{
                "-f","concat","-safe","0","-i",
                textFile,
                "-i",preAudioInputdest.getAbsolutePath(),
                "-vsync","cfr","-r","2","-pix_fmt", "yuv420p", //noted
               dest.getAbsolutePath()};
        try {
            Dialog dialog = new Dialog(this);
            ffmpeg.execute(cmd, new ExecuteBinaryResponseHandler() {
                @Override
                public void onFailure(String s) {
                    Log.d("FFMPEG", "fail "+s);
                }

                @Override
                public void onSuccess(String s) {
                    Log.d("FFMPEG", "Success");
                    dialog.dismiss();
                }

                @Override
                public void onProgress(String s) {
                    Log.d("FFMPEG", "Progress");
                    dialog.setContentView(R.layout.dialog_progressing);
                    ImageView imvProgress = dialog.findViewById(R.id.imv_progress);
                    Glide.with(getBaseContext()).load(R.raw.progressing_bar).into(imvProgress);
                    dialog.show();
                }

                @Override
                public void onStart() {
                    Log.d("FFMPEG", "start");
                }

                @Override
                public void onFinish() {
                    Log.d("FFMPEG", "finish");
                    vvSlideshow.setVideoPath(dest.getAbsolutePath());
                    vvSlideshow.start();
                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            e.printStackTrace();
        }
    }

    private void prepareAudioFile(String audioPath){
        preAudioInputdest = new File(appRoot,"preAudioInputFiles.mp3");
        String[] audioCmnd = {"-y", "-i", audioPath, "-ss", "60" , "-t", String.valueOf(secPerImage*paths.size()+1), preAudioInputdest.getAbsolutePath()};
        excuteFFMPEG(audioCmnd);
    }

    private File getTextFile2() throws IOException {
        File file = new File(appRoot,"preinputFiles2.txt");
        FileOutputStream out = new FileOutputStream(file, false);
        PrintWriter writer = new PrintWriter(out);
        StringBuilder builder = new StringBuilder();
        for (String path : paths) {
            if (path != null) {
                builder.append("file ");
                builder.append("'");
                builder.append(path);
                builder.append("'\n");
                builder.append("duration ").append(secPerImage);
                builder.append("\n");
            }
        }
        builder.append("file ");
        builder.append("'");
        builder.append(paths.get(paths.size()-1));
        builder.append("'\n");
        builder.deleteCharAt(builder.length()-1);
        Log.d("TEST",builder.toString());
        String text = builder.toString();
        writer.print(text);
        writer.close();
        out.close();
        return file;
    }

    private void excuteFFMPEG(String[] command) {
        try {
            ffmpeg.execute(command, new ExecuteBinaryResponseHandler() {
                @Override
                public void onFailure(String s) {
                    Log.d("FFMPEG", "fail "+s);
                }

                @Override
                public void onSuccess(String s) {
                    Log.d("FFMPEG", "Success");
                }

                @Override
                public void onProgress(String s) {
                    Log.d("FFMPEG", "Progress");
                }

                @Override
                public void onStart() {
                    Log.d("FFMPEG", "start");
                }

                @Override
                public void onFinish() {
                    Log.d("FFMPEG", "finish");
                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            e.printStackTrace();
        }
    }
    private void openSlidePerSecond() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_slide_per_second);
        dialog.show();
        ImageView imvCheck = dialog.findViewById(R.id.imv_dialog_sldie_per_sec_check);
        ImageView imvCancel = dialog.findViewById(R.id.imv_dialog_sldie_per_sec_cancel);
        EditText edtSecPerImage = dialog.findViewById(R.id.edt_dialog_slide_per_image);

        imvCancel.setOnClickListener(view -> dialog.dismiss());
        String editedSecPerImage = String.valueOf(secPerImage);
        edtSecPerImage.setHint(editedSecPerImage);
        imvCheck.setOnClickListener(view -> {
            secPerImage = Integer.parseInt(edtSecPerImage.getText().toString());
            dialog.dismiss();
        });
    }
    private void init() {
        paths = getIntent().getStringArrayListExtra(KEY_GET_IMAGES_TO_CREATE_SLIDE_SHOW);
        imvBack = findViewById(R.id.imv_slide_show_back);
        tvAdd = findViewById(R.id.tv_slide_show_add_iamges);
        tvSlidePerSec = findViewById(R.id.tv_slide_show_slides_per_second);
        fabCrete = findViewById(R.id.fab_slide_show_create);
        vvSlideshow = findViewById(R.id.vv_made_slide_show);
        tvAddAudio=findViewById(R.id.tv_slide_show_add_audio);
    }
    private void loadFFMPEG() {
        ffmpeg = FFmpeg.getInstance(this);
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {

                @Override
                public void onStart() {}

                @Override
                public void onFailure() {}

                @Override
                public void onSuccess() {}

                @Override
                public void onFinish() {}
            });
        } catch (FFmpegNotSupportedException e) {
            // Handle if FFmpeg is not supported by device
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == KEY_GET_AUDIO) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK
                if (data != null) {
                    audioPath = data.getStringExtra(AddAudioActivity.KEY_SEND_AUDIO);
                    Log.d("TEST",audioPath);
                    prepareAudioFile(audioPath);
                }
            }
        }
    }
}