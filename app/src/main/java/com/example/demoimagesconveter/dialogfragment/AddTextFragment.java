package com.example.demoimagesconveter.dialogfragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoimagesconveter.R;
import com.example.demoimagesconveter.adapter.ColorPickerAdapter;

public class AddTextFragment extends DialogFragment implements ColorPickerAdapter.onColorPicker {

    public static final String TAG = AddTextFragment.class.getSimpleName();
    public static final String EXTRA_INPUT_TEXT = "extra_input_text";
    public static final String EXTRA_COLOR_CODE = "extra_color_code";
    RecyclerView rvColorPicker;
    EditText edtAdd;
    TextView tvDone;
    AddTextEditor addTextEditor;
    int passTextColor;

    public AddTextFragment() {
        // Required empty public constructor
    }

    public static void show(@NonNull AppCompatActivity appCompatActivity,
                            @NonNull String inputText,
                            @ColorInt int colorCode) {
        Bundle args = new Bundle();
        args.putString(EXTRA_INPUT_TEXT, inputText);
        args.putInt(EXTRA_COLOR_CODE, colorCode);
        AddTextFragment fragment = new AddTextFragment();
        fragment.setArguments(args);
        fragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
    }

    public static void show(@NonNull AppCompatActivity appCompatActivity) {
        show(appCompatActivity,"", ContextCompat.getColor(appCompatActivity, R.color.white));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        addTextEditor = (AddTextEditor) context;
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
        return inflater.inflate(R.layout.fragment_add_text, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        fetchView();
        registerevent();
    }

    private void registerevent() {
        tvDone.setOnClickListener(v -> {
            String inputText = edtAdd.getText().toString().trim();
            Log.d("onDone","Add text: "+inputText+"-text color: "+passTextColor);
            dismiss();
            if (!TextUtils.isEmpty(inputText) && addTextEditor!=null){
                addTextEditor.onDone(inputText,passTextColor);
                Log.d("onDone","Add text: "+inputText+"-text color: "+passTextColor);
            }
        });
    }

    private void fetchView() {
        ColorPickerAdapter colorPickerAdapter = new ColorPickerAdapter(getContext(),this);
        rvColorPicker.setAdapter(colorPickerAdapter);
        rvColorPicker.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
    }

    private void init(View view) {
        rvColorPicker = view.findViewById(R.id.rv_add_text_color_picker);
        edtAdd = view.findViewById(R.id.edt_dialog_add_text);
        tvDone = view.findViewById(R.id.tv_add_text_done);
    }

    @Override
    public void onItemClickListener(int pos) {
        edtAdd.setTextColor(pos);
        passTextColor = pos;
    }

    public interface AddTextEditor {
        void onDone(String inputText, int colorCode);
    }

}