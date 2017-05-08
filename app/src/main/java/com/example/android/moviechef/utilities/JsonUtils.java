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

        Movie[] movies = null;

        JSONObject moviesJsonFull = new JSONObject(moviesString);
        if (moviesJsonFull.has(RESULTS)) {
            // Parse out the array of movies from the full request
            JSONArray moviesJsonArray = moviesJsonFull.getJSONArray(RESULTS);

            for (int i = 0; i < moviesJsonArray.length(); i++) {
                String title = "";
                String imageUrl = "";
                String overview = "";
                String id = "";

                // Get a single movie
                JSONObject jsonMovie = moviesJsonArray.getJSONObject(i);


                title = setValueForKey(jsonMovie, "title");
                imageUrl = setValueForKey(jsonMovie, "poster_path");
                overview = setValueForKey(jsonMovie, "overview");
                id = setValueForKey(jsonMovie, "id");

                Movie movie = new Movie(title, imageUrl, overview, id);
                try {
                    movies[i] = movie;
                } catch (NullPointerException npe) {
                    npe.printStackTrace();
                    movies[i] = null;
                }
            }
        }
        return movies;
    }

    /**
     * Take in movie JSON object and return the value for the key.
     * @param movie JSON object containing movie details.
     * @param key The key to be looked up in the movie JSON object.
     * @return The value for the key in the given JSON movie object.
     */
    private static String setValueForKey(JSONObject movie, String key) {
        if (movie.has(key)) {
            try {
                return movie.getString(key);
            } catch (JSONException jse) {
                jse.printStackTrace();
                return "n/a";
            }
        } else {
            return "n/a";
        }
    }
}
