package com.example.android.moviechef;

/**
 * Represents a given movie object including basic information about the given film.
 */

public final class Movie {
    // The movie's title.
    private final String mTitle;

    // The movie's cover photo/image.
    private final String mImageUrl;

    // Brief synopsis of the movie's plot/theme.
    private final String mOverview;

    // The movie's unique ID.
    private final String mId;

    // Average rating from users for the movie.
    private final Double mAvgRating;

    // The movie's original release date.
    private final String mReleaseDate;

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
