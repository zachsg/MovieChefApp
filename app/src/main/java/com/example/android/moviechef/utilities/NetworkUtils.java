package com.example.android.moviechef.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

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

    private static final String POPULAR_MOVIES = "/movie/popular";
    private static final String TOP_RATED_MOVIES = "/movie/top_rated";

    public static String buildUrl(String sort) {
        if (sort.equals(POPULAR_MOVIES)) {
            return MOVIES_BASE_URL + POPULAR_MOVIES;
        } else if (sort.equals(TOP_RATED_MOVIES)) {
            return MOVIES_BASE_URL + TOP_RATED_MOVIES;
        } else {
            return MOVIES_BASE_URL;
        }
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
