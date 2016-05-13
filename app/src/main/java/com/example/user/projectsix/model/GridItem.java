package com.example.user.projectsix.model;


import android.graphics.Bitmap;

public class GridItem {

    private Bitmap mImage;
    private String mTitle;

    public GridItem() {
        super();
    }

    public Bitmap getImage() {
        return mImage;
    }

    public void setImage(Bitmap mImage) {
        this.mImage = mImage;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
