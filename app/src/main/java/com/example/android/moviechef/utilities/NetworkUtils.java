package com.example.android.moviechef.utilities;

/**
 * Utilities for communicating with themoviedb.org.
 */

public final class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    private static final String MOVIES_STARTER_URL =
            "https://api.themoviedb.org/3/movie/550?api_key=";

    // Be sure to add your API_KEY here
    private static final String API_KEY = "";
    private static final String MOVIES_BASE_URL = MOVIES_STARTER_URL + API_KEY;
}
