package com.example.demoimagesconveter.view.Gallery;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.common.BaseActivity;

import java.io.File;

public class EditImageActivity extends BaseActivity {

    public static final String KEY_GET_IMAGE_PATH = "KEY_GET_IMAGE_PATH";
    ImageView imvBack,imvEdited,imvMenu;
    String path;

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
            PopupMenu popupMenu = new PopupMenu(EditImageActivity.this,imvMenu);
            popupMenu.getMenuInflater().inflate(R.menu.menu_edit_image,popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()){
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
            if (deleteImage){
                showtoast("Delete Successfully");
                dialog.dismiss();
                finish();
            }else{
                showtoast("Unsuccessfully");
                dialog.dismiss();
            }
        });
    }

    private void showDialogInfo() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_image_information);
        dialog.show();
        String dir = "Directory: "+ path;
        TextView tvDir = dialog.findViewById(R.id.tv_dialog_info_dir);
        tvDir.setText(dir);
        File file = new File(path);
        String capacity ="Capacity: " + file.length() / 1000 + " Kb";
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(new File(path).getAbsolutePath(), options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        TextView tvCapacity = dialog.findViewById(R.id.tv_dialog_info_capacity);
        tvCapacity.setText(capacity);
        TextView tvSize = dialog.findViewById(R.id.tv_dialog_info_size);
        String size = "Size: "+imageHeight+" x "+imageWidth;
        tvSize.setText(size);

    }

    private void init() {
        imvBack = findViewById(R.id.imv_edit_iamge_back);
        imvEdited = findViewById(R.id.imv_edit_image);
        imvMenu = findViewById(R.id.imv_edit_image_menu);
        path = getIntent().getStringExtra(KEY_GET_IMAGE_PATH);
        Glide.with(this).load(path).into(imvEdited);
    }
}