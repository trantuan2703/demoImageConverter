package com.example.demoimagesconveter.model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class ModelFrame implements Serializable {
    private String title;
    private String date;
    private Bitmap frame;
    private String path;

    public ModelFrame() {
    }

    public ModelFrame(String title, String date, Bitmap frame) {
        this.title = title;
        this.date = date;
        this.frame = frame;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Bitmap getFrame() {
        return frame;
    }

    public void setFrame(Bitmap frame) {
        this.frame = frame;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
