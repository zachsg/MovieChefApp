package com.example.android.moviechef;

/**
 * Created by zachg on 5/6/17.
 */

public class Movie {
    private String mTitle;
    private String mImageUrl;
    private String mOverview;
    private String mId;
    private Double mAvgRating;
    private String mReleaseDate;

    public Movie() {
        mTitle = "";
        mImageUrl = "";
        mId = "";
        mOverview = "";
        mAvgRating = 0.0;
        mReleaseDate = "";
    }

    public Movie(String title, String imageUrl, String overview,
                 String id, Double avgRating, String releaseDate) {
        mTitle = title;
        mImageUrl = imageUrl;
        mOverview = overview;
        mId = id;
        mAvgRating = avgRating;
        mReleaseDate = releaseDate;
    }

    /* Getters */

    public String getmTitle() {
        return mTitle;
    }

    public String getmImageUrl() { return mImageUrl; }

    public String getmOverview() { return mOverview; }

    public String getmId() { return mId; }

    public Double getmAvgRating() { return mAvgRating; }

    public String getmReleaseDate() { return mReleaseDate; }
}
