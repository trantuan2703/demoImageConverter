package com.example.demoimagesconveter.model;

import android.graphics.Bitmap;

public class modelVideo {
    private String title;
    private String path;
    private Bitmap thumbNail;
    private String duration;

    public modelVideo() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Bitmap getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(Bitmap thumbNail) {
        this.thumbNail = thumbNail;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
