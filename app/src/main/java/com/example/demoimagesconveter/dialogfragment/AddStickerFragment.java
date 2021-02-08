package com.example.demoimagesconveter.dialogfragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.adapter.StickerAdapter;

import java.util.ArrayList;

public class AddStickerFragment extends DialogFragment implements StickerAdapter.onStickerClickListener {
    public static final String TAG = AddStickerFragment.class.getSimpleName();
    public static final String KEY_GET_EMOJI ="KEY_GET_EMOJI";
    ArrayList<String> emojisList;
    RecyclerView rvSticker;
    AddStickerEditor addStickerEditor;

    public AddStickerFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        addStickerEditor = (AddStickerEditor) context;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        emojisList = getArguments().getStringArrayList(KEY_GET_EMOJI);
        return inflater.inflate(R.layout.fragment_add_sticker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }


    private void init(View view) {
        view.findViewById(R.id.tv_add_emoji_back).setOnClickListener(v -> dismiss());
        rvSticker = view.findViewById(R.id.rv_emoji_list);
        StickerAdapter stickerAdapter = new StickerAdapter(emojisList,this);
        rvSticker.setAdapter(stickerAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5);
        rvSticker.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onClick(int pos) {
        handleChooseSticker(emojisList.get(pos));
    }

    private void handleChooseSticker(String s) {
        dismiss();
        addStickerEditor.onStickerDone(s);
    }

    public interface AddStickerEditor{
        void onStickerDone(String s);
    }
}