package com.example.riamon_v.java_epicture_2017.ListManagment;

import android.util.Log;

/**
 * Created by riamon_v on 09/02/2018.
 */

public class CardClass {
    private String url;
    private String id;
    private String title;
    public String fav;
    private int idResources = -1;

    public CardClass(String title, String url, String id, String fav) {
        this.title = title;
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

}
