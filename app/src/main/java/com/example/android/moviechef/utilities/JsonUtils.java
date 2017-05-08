package com.example.android.moviechef.utilities;

import android.content.Context;

import com.example.android.moviechef.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Utility functions to help with handling JSON received from themoviedb.org API.
 */

public final class JsonUtils {

    public static Movie[] getMoviesFromJson(Context context, String moviesString)
        throws JSONException {

        // Base level array of all movies returned by query
        final String RESULTS = "results";

        Movie[] movies;

        JSONObject moviesJsonFull = new JSONObject(moviesString);
        if (moviesJsonFull.has(RESULTS)) {
            // Parse out the array of movies from the full request
            JSONArray moviesJsonArray = moviesJsonFull.getJSONArray(RESULTS);

            for (int i = 0; i < moviesJsonArray.length(); i++) {

            }
        }
    }
}
