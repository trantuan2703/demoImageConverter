package com.example.demoimagesconveter.view.Gallery;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.common.BaseActivity;
import com.example.demoimagesconveter.dialogfragment.AddStickerFragment;
import com.example.demoimagesconveter.dialogfragment.AddTextFragment;
import com.example.demoimagesconveter.view.Setting.SettingActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropFragment;
import com.yalantis.ucrop.UCropFragmentCallback;
import com.yalantis.ucrop.view.UCropView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.SaveSettings;
import ja.burhanrashid52.photoeditor.TextStyleBuilder;

public class EditImageActivity extends BaseActivity implements UCropFragmentCallback,
        AddTextFragment.AddTextEditor {
    public static final String KEY_GET_IMAGE_PATH = "KEY_GET_IMAGE_PATH";
    UCropView uCropView;
    ImageView imvBack, imvEdited, imvMenu, imvSave;
    String path;
    TextView tvCrop, tvAddText,tvBrush,tvSticker;
    LinearLayout llBottomBar;
    TextView tvTitle;
    AppBarLayout abEdited;
    UCropFragment uCropFragment;
    PhotoEditorView photoEditorView;
    PhotoEditor photoEditor;
    String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);
        init();
        registerevent();
    }

    @SuppressLint("NonConstantResourceId")
    private void registerevent() {
        imvBack.setOnClickListener(view -> finish());
        imvMenu.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(EditImageActivity.this, imvMenu);
            popupMenu.getMenuInflater().inflate(R.menu.menu_edit_image, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.menu_edit_image_info:
                        showDialogInfo();
                        break;
                    case R.id.menu_edit_image_share:
                        shareIamgesToOthers();
                        break;
                    case R.id.menu_edit_image_delete:
                        deleteImage();
                        break;
                    default:
                        break;
                }
                return true;
            });
            popupMenu.show();
        });
        tvCrop.setOnClickListener(view -> handleCropView());
        tvAddText.setOnClickListener(v -> AddTextFragment.show(this));
        tvBrush.setOnClickListener(v -> handleBrush());
        tvSticker.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(AddStickerFragment.KEY_GET_EMOJI,PhotoEditor.getEmojis(this));
            AddStickerFragment addStickerFragment = new AddStickerFragment();
            addStickerFragment.setArguments(bundle);
            addStickerFragment.show(getSupportFragmentManager(),AddStickerFragment.TAG);
        });
    }

    private void handleBrush() {

    }

    private void handleCropView() {
        llBottomBar.setVisibility(View.GONE);
        abEdited.setVisibility(View.GONE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());
        String destinationFileName = "CropImage_" + currentDateAndTime;
        String type;
        SharedPreferences pref = getSharedPreferences(SettingActivity.KEY_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        type = pref.getString(SettingActivity.KEY_SHARED_PREFERENCE_TYPE_FORMAT, "JPEG");
        if (type.equals("JPEG")) {
            destinationFileName += ".jpeg";
        } else {
            destinationFileName += ".png";
        }

        Uri uri = Uri.fromFile(new File((path)));
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(root + "/DemoImagesConverter", destinationFileName)));
        UCrop.Options options = new UCrop.Options();
        options.setFreeStyleCropEnabled(true);
        uCropFragment = uCrop.getFragment(uCrop.getIntent(this).getExtras());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fr_edit_image, uCropFragment, UCropFragment.TAG)
                .commitAllowingStateLoss();
        uCrop.start(this);
    }

    private void shareIamgesToOthers() {
        Intent shareIntent = new Intent();
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(path)));
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, "Share image"));
    }

    private void deleteImage() {
        File file = new File(path);
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_delete_this);
        dialog.show();
        ImageView imvAccept = dialog.findViewById(R.id.imv_dialog_delete_accept);
        ImageView imvDecline = dialog.findViewById(R.id.imv_dialog_delete_decline);
        imvDecline.setOnClickListener(view -> dialog.dismiss());
        imvAccept.setOnClickListener(view -> {
            boolean deleteImage = file.delete();
            if (deleteImage) {
                showtoast("Delete Successfully");
                dialog.dismiss();
                finish();
            } else {
                showtoast("Unsuccessfully");
                dialog.dismiss();
            }
        });
    }

    private void showDialogInfo() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_image_information);
        dialog.show();
        String dir = "Directory: " + path;
        TextView tvDir = dialog.findViewById(R.id.tv_dialog_info_dir);
        tvDir.setText(dir);
        File file = new File(path);
        String capacity = "Capacity: " + file.length() / 1000 + " Kb";
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(new File(path).getAbsolutePath(), options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        TextView tvCapacity = dialog.findViewById(R.id.tv_dialog_info_capacity);
        tvCapacity.setText(capacity);
        TextView tvSize = dialog.findViewById(R.id.tv_dialog_info_size);
        String size = "Size: " + imageHeight + " x " + imageWidth;
        tvSize.setText(size);
    }

    private void init() {
        imvBack = findViewById(R.id.imv_edit_iamge_back);
        imvMenu = findViewById(R.id.imv_edit_image_menu);
        path = getIntent().getStringExtra(KEY_GET_IMAGE_PATH);
        tvCrop = findViewById(R.id.tv_crop_image);
        uCropView = findViewById(R.id.ucrop);
        llBottomBar = findViewById(R.id.ll_bottom_bar);
        tvTitle = findViewById(R.id.tv_edit_image_title);
        imvSave = findViewById(R.id.imv_save_crop);
        abEdited = findViewById(R.id.ab_edit_image);
        tvAddText = findViewById(R.id.tv_add_text);
        tvBrush = findViewById(R.id.tv_brush);
        tvSticker = findViewById(R.id.tv_sticker);

        photoEditorView = findViewById(R.id.photoEditorView);
        photoEditorView.getSource().setImageBitmap(BitmapFactory.decodeFile(path));
        photoEditor = new PhotoEditor.Builder(this, photoEditorView)
                .setPinchTextScalable(true)
                .build();

        SharedPreferences pref = getSharedPreferences(SettingActivity.KEY_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        type = pref.getString(SettingActivity.KEY_SHARED_PREFERENCE_TYPE_FORMAT, "JPEG");
    }

    @Override
    public void loadingProgress(boolean showLoader) {

    }

    @Override
    public void onCropFinish(UCropFragment.UCropResult result) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP && data != null) {
            final Uri resultUri = UCrop.getOutput(data);
            handleResultCrop(resultUri);
            showtoast("Successful");
        } else {
            showtoast("Can't Crop Image");
        }
    }

    private void handleResultCrop(Uri resultUri) {
        abEdited.setVisibility(View.VISIBLE);
        imvMenu.setVisibility(View.GONE);
        imvEdited.setVisibility(View.VISIBLE);
        llBottomBar.setVisibility(View.VISIBLE);
        imvSave.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction()
                .remove(uCropFragment)
                .commit();
        Glide.with(this).load(resultUri).into(imvEdited);
        imvSave.setOnClickListener(v -> {
            Dialog deleteDialog = new Dialog(this);
            deleteDialog.setContentView(R.layout.dialog_delete_this);
            deleteDialog.show();
            TextView tvAlert = deleteDialog.findViewById(R.id.tv_alert_text);
            ImageView imvAccept = deleteDialog.findViewById(R.id.imv_dialog_delete_accept);
            ImageView imvDecline = deleteDialog.findViewById(R.id.imv_dialog_delete_decline);
            tvAlert.setText(R.string.do_you_want_to_save_this);
            imvAccept.setOnClickListener(view -> {
                deleteDialog.dismiss();
                imvSave.setVisibility(View.GONE);
                imvMenu.setVisibility(View.VISIBLE);
            });
            imvDecline.setOnClickListener(view -> {
                File file = new File(resultUri.getPath());
                boolean deleteImage = file.delete();
                if (deleteImage) {
                    Glide.with(this).load(resultUri).into(imvEdited);
                }
                deleteDialog.dismiss();
                imvSave.setVisibility(View.GONE);
                imvMenu.setVisibility(View.VISIBLE);
            });
        });
    }

    @Override
    public void onDone(String inputText, int colorCode) {
        final TextStyleBuilder styleBuilder = new TextStyleBuilder();
        styleBuilder.withTextColor(colorCode);
        photoEditor.addText(inputText, styleBuilder);
        imvMenu.setVisibility(View.GONE);
        imvSave.setVisibility(View.VISIBLE);
        imvSave.setOnClickListener(v ->{
            showtoast("Saving");
            saveEditedImage();
        });
    }

    @SuppressLint("MissingPermission")
    private void saveEditedImage() {
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File imageDir = new File(root, "/DemoImagesConverter");
        String fname = String.valueOf(System.currentTimeMillis());
        if (type.equals("JPEG")) {
            fname += ".jpeg";
        } else {
            fname += ".png";
        }
        File file = new File(imageDir, fname);
        SaveSettings saveSettings = new SaveSettings.Builder()
                .setClearViewsEnabled(true)
                .setTransparencyEnabled(true)
                .build();
        photoEditor.saveAsFile(file.getAbsolutePath(), saveSettings, new PhotoEditor.OnSaveListener() {
            @Override
            public void onSuccess(@NonNull String imagePath) {
                showtoast("Success");
                photoEditorView.getSource().setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                imvSave.setVisibility(View.GONE);
                imvMenu.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(@NonNull Exception exception) {
                showtoast("Fail");
            }
        });
    }

}