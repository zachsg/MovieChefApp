package com.example.android.moviechef;

/**
 * Created by zachg on 5/6/17.
 */

public class Movie {
    private String mTitle;
    private String mImageUrl;
    private String mOverview;
    private String mId;

    public Movie() {
        mTitle = "";
        mImageUrl = "";
        mId = "";
        mOverview = "";
    }

    public Movie(String title, String imageUrl, String overview, String id) {
        mTitle = title;
        mImageUrl = imageUrl;
        mOverview = overview;
        mId = id;
    }

    /* Getters */

    public String getmTitle() {
        return mTitle;
    }

    public String getmImageUrl() { return mImageUrl; }

    public String getmOverview() { return mOverview; }

    public String getmId() { return mId; }
}
