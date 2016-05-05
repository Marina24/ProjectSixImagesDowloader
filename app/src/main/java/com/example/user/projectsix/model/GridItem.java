package com.example.user.projectsix.model;


import android.graphics.Bitmap;

public class GridItem {

    private Bitmap mImage;
    private String mTitle;

    public GridItem() {
        super();
    }

    public Bitmap getmImage() {
        return mImage;
    }

    public void setmImage(Bitmap mImage) {
        this.mImage = mImage;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
