package com.example.demoimagesconveter.model;

import java.io.Serializable;

public class ModelVideo implements Serializable {
    private String title;
    private String path;
    private String duration;
    private String showDuration;

    public ModelVideo() {
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getShowDuration() {
        return showDuration;
    }

    public void setShowDuration(String showDuration) {
        this.showDuration = showDuration;
    }

    @Override
    public String toString() {
        return "ModelVideo{" +
                "title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", duration='" + duration + '\'' +
                ", showDuration='" + showDuration + '\'' +
                '}';
    }
}
