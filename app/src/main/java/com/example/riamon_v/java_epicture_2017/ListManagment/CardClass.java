package com.example.riamon_v.java_epicture_2017.ListManagment;

import android.util.Log;

import java.io.File;
import java.io.Serializable;

/**
 * Card class display on recycler view
 */
public class CardClass implements Serializable {
    private String url;
    private String id;
    private String title;
    private String content;
    public String fav;
    private int idResources = -1;

    public CardClass(String title, String content, String url, String id, String fav) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.id = id;
        this.fav = fav;
        //this.idResources = idResources;
    }


    public int getIdResources() {
        return idResources;
    }

    public void setIdResources(int idResources) {
        this.idResources = idResources;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
