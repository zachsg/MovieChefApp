package com.example.android.moviechef.utilities;

import android.content.Context;
import android.util.Log;

import com.example.android.moviechef.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility functions to help with handling JSON received from themoviedb.org API.
 */

public final class JsonUtils {

    public static List<Movie> getMoviesFromJson(Context context, String moviesString)
        throws JSONException {

        // Base level array of all movies returned by query
        final String RESULTS = "results";

        List<Movie> movies = new ArrayList<>();

        JSONObject moviesJsonFull = new JSONObject(moviesString);
        if (moviesJsonFull.has(RESULTS)) {
            // Parse out the array of movies from the full request
            JSONArray moviesJsonArray = moviesJsonFull.getJSONArray(RESULTS);

            for (int i = 0; i < moviesJsonArray.length(); i++) {
                // Get a single movie
                JSONObject jsonMovie = moviesJsonArray.getJSONObject(i);

                String title = setValueForKey(jsonMovie, "title").toString();
                String imageUrl = setValueForKey(jsonMovie, "poster_path").toString();
                String overview = setValueForKey(jsonMovie, "overview").toString();
                String id = setValueForKey(jsonMovie, "id").toString();
                String releaseDate = setValueForKey(jsonMovie, "release_date").toString();
                String duration = setValueForKey(jsonMovie, "runtime").toString();
                Double avgRating =
                        Double.parseDouble(setValueForKey(jsonMovie, "vote_average").toString());

                Log.v("JsonUtils", title + "\n" + imageUrl + "\n" + overview + "\n" + id);

                Movie movie =
                        new Movie(title, imageUrl, overview, id, avgRating, releaseDate, duration);
                movies.add(i, movie);
            }
        } else {
            Log.v("JsonUtils", "No valid movies found in JSON :(");
        }
        return movies;
    }

    /**
     * Take in movie JSON object and return the value for the key.
     * @param movie JSON object containing movie details.
     * @param key The key to be looked up in the movie JSON object.
     * @return The value for the key in the given JSON movie object.
     */
    private static Object setValueForKey(JSONObject movie, String key) {
        if (movie.has(key)) {
            try {
                if (key.equals("vote_average")) {
                    return movie.getDouble(key);
                } else {
                    return movie.getString(key);
                }
            } catch (JSONException jse) {
                jse.printStackTrace();
                if (key.equals("vote_average")) {
                    return 0.0;
                } else {
                    return "n/a";
                }
            }
        } else {
            if (key.equals("vote_average")) {
                return 0.0;
            } else {
                return "n/a";
            }
        }
    }
}
