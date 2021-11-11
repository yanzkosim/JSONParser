package com.example.jsonparser;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Detail {
    private final String title;
    private final String status;
    private final String type;
    private final String episodes;
    private final String score;

    public Detail(String title, String status,
                  String episodes, String score, String type) {
        this.title = title;
        this.status = status;
        this.type = type;
        this.episodes = episodes;
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public String getEpisodes() {
        return episodes;
    }

    public String getScore() {
        return score;
    }

    public String getType() {
        return type;
    }
}
