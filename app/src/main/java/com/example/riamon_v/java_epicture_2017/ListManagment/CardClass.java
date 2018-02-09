package com.example.riamon_v.java_epicture_2017.ListManagment;

/**
 * Created by riamon_v on 09/02/2018.
 */

public class CardClass {
    private String title;
    private int idResources;

    public CardClass(String title, int idResources) {

        this.title = title;
        this.idResources = idResources;
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
}
