package com.example.demoimagesconveter.view.Setting;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.common.BaseActivity;
import com.example.demoimagesconveter.view.MainActivity;

import java.util.ArrayList;

public class SettingActivity extends BaseActivity {
    public static final String KEY_SHARED_PREFERENCE="KEY_SHARED_PREFERENCE";
    public static final String KEY_SHARED_PREFERENCE_TYPE_FORMAT="TYPE_FORMAT";
    public static final String KEY_SHARED_PREFERENCE_QUALITY="QUALITY";
    public static final String KEY_SHARED_PREFERENCE_SIZE="SIZE";

    ImageView imvBack;
    TextView tvFileFormat,tvQuality,tvSize;
    SharedPreferences preferences;
    String yourTypeFormat, yourQuality, yourSize;
    ArrayList<RadioButton> buttons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
        registerEvent();
    }

    @SuppressLint("NonConstantResourceId")
    private void openFileFormatSetting() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_file_format);
        dialog.show();
        RadioGroup rgChange = dialog.findViewById(R.id.rg_setting_change);
        RadioButton rbJpeg= dialog.findViewById(R.id.rb_jpeg);
        RadioButton rbPng = dialog.findViewById(R.id.rb_png);
        ImageView imvCheck = dialog.findViewById(R.id.imv_dialog_file_format_check);
        ImageView imvClose = dialog.findViewById(R.id.imv_dialog_file_format_close);

        buttons.add(rbJpeg);buttons.add(rbPng);
        fetchDialogView(new String[]{"JPEG","PNG"},buttons,yourTypeFormat);
        imvClose.setOnClickListener(view -> dialog.dismiss());
        rgChange.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i){
                case R.id.rb_jpeg:
                    yourTypeFormat="JPEG";
                    break;
                case R.id.rb_png:
                    yourTypeFormat="PNG";
                    break;
            }
        });
        imvCheck.setOnClickListener(view -> SaveYourData(KEY_SHARED_PREFERENCE_TYPE_FORMAT,yourTypeFormat,tvFileFormat,dialog));
    }

    private void fetchDialogView(String[] buttonTexts, ArrayList<RadioButton> buttons,String s) {
        for (int i = 0; i < buttonTexts.length; i++) {
            if (s.equals(buttonTexts[i])){
                buttons.get(i).setChecked(true);
                break;
            }
        }
        buttons.clear();
    }

    private void registerEvent() {
        imvBack.setOnClickListener(view -> {
            Intent intent = new Intent(SettingActivity.this, MainActivity.class);
            startActivity(intent);
        });
        tvFileFormat.setOnClickListener(view -> openFileFormatSetting());
        tvQuality.setOnClickListener(view -> openQualityDialog());
        tvSize.setOnClickListener(view -> openSizeDialog());
    }

    @SuppressLint("NonConstantResourceId")
    private void openSizeDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_size);
        dialog.show();
        RadioGroup rgChange = dialog.findViewById(R.id.rg_size_change);
        RadioButton rb05x = dialog.findViewById(R.id.rb_05x);
        RadioButton rb1x = dialog.findViewById(R.id.rb_1x);
        RadioButton rb15x = dialog.findViewById(R.id.rb_15x);
        RadioButton rb2x = dialog.findViewById(R.id.rb_2x);
        RadioButton rb3x = dialog.findViewById(R.id.rb_3x);
        ImageView imvClose = dialog.findViewById(R.id.imv_dialog_size_close);
        ImageView imvCheck = dialog.findViewById(R.id.imv_dialog_size_check);
        buttons.add(rb05x);buttons.add(rb1x);buttons.add(rb15x);buttons.add(rb2x);buttons.add(rb3x);
        fetchDialogView(new String[]{"0.5x","1x","1.5x","2x","3x"},buttons,yourSize);
        imvClose.setOnClickListener(view -> dialog.dismiss());
        rgChange.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i){
                case R.id.rb_05x:
                    yourSize="0.5x";
                    break;
                case R.id.rb_1x:
                    yourSize="1x";
                    break;
                case R.id.rb_15x:
                    yourSize="1.5x";
                    break;
                case R.id.rb_2x:
                    yourSize="2x";
                    break;
                case R.id.rb_3x:
                    yourSize="3x";
                    break;
            }
        });
        imvCheck.setOnClickListener(view -> SaveYourData(KEY_SHARED_PREFERENCE_SIZE,yourSize,tvSize,dialog));
    }

    @SuppressLint("NonConstantResourceId")
    private void openQualityDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_quality);
        dialog.show();
        RadioGroup rgChange = dialog.findViewById(R.id.rg_quality_change);
        RadioButton rbBest = dialog.findViewById(R.id.rb_best);
        RadioButton rbVeryHigh = dialog.findViewById(R.id.rb_very_high);
        RadioButton rbHigh = dialog.findViewById(R.id.rb_high);
        RadioButton rbMedium = dialog.findViewById(R.id.rb_medium);
        RadioButton rbLow = dialog.findViewById(R.id.rb_low);
        ImageView imvCheck = dialog.findViewById(R.id.imv_dialog_quality_check);
        ImageView imvClose = dialog.findViewById(R.id.imv_dialog_quality_close);
        buttons.add(rbBest);buttons.add(rbVeryHigh);buttons.add(rbHigh);buttons.add(rbMedium);buttons.add(rbLow);
        fetchDialogView(new String[]{"Best","Very High","High","Medium","Low"},buttons,yourQuality);

        rgChange.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i){
                case R.id.rb_best:
                    yourQuality="Best";
                    break;
                case R.id.rb_very_high:
                    yourQuality="Very High";
                    break;
                case R.id.rb_high:
                    yourQuality="High";
                    break;
                case R.id.rb_medium:
                    yourQuality="Medium";
                    break;
                case R.id.rb_low:
                    yourQuality="Low";
                    break;
            }
        });
        imvClose.setOnClickListener(view -> dialog.dismiss());
        imvCheck.setOnClickListener(view -> SaveYourData(KEY_SHARED_PREFERENCE_QUALITY,yourQuality, tvQuality,dialog));
    }

    private void init() {
        imvBack=findViewById(R.id.imv_setting_back);
        tvFileFormat=findViewById(R.id.tv_setting_file_format);
        tvQuality = findViewById(R.id.tv_setting_quality);
        tvSize = findViewById(R.id.tv_setting_size);
        preferences = getApplicationContext().getSharedPreferences(KEY_SHARED_PREFERENCE, MODE_PRIVATE);
        yourTypeFormat=preferences.getString(KEY_SHARED_PREFERENCE_TYPE_FORMAT,"JPEG");
        yourQuality =preferences.getString(KEY_SHARED_PREFERENCE_QUALITY,"High");
        yourSize = preferences.getString(KEY_SHARED_PREFERENCE_SIZE,"1x");
        Log.d("TEST_SP","type: "+yourTypeFormat+"-quality: "+ yourQuality+"-size: ");
        tvFileFormat.setText(yourTypeFormat);
        tvQuality.setText(yourQuality);
        tvSize.setText(yourSize);
    }

    private void SaveYourData(String key, String value, TextView textView,Dialog dialog){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.apply();
        dialog.dismiss();
        textView.setText(value);
        showtoast("Changing successfully!");
    }
}