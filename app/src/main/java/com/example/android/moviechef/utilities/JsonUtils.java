package com.example.android.moviechef.utilities;

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

    /**
     * Take a string representation of movies and return a list of individual Movie objects.
     * @param moviesString The movies retrieved from the server in String format.
     * @return The parsed list of Movie objects from passed moviesString.
     * @throws JSONException In case conversion of moviesString to moviesJsonFull fails.
     */
    public static List<Movie> getMoviesFromJson(String moviesString)
        throws JSONException {

        // Base level JSON array of all movies returned by query from themoviedb.org API.
        final String RESULTS = "results";

        List<Movie> movies = new ArrayList<>();

        JSONObject moviesJsonFull = new JSONObject(moviesString);
        if (moviesJsonFull.has(RESULTS)) {
            // Parse out the array of movies from the full request
            JSONArray moviesJsonArray = moviesJsonFull.getJSONArray(RESULTS);

            /* Parse out each Movie from the JSON array and add it to list of Movie objects
             * to be returned.
             */
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

                // Construct new Movie object with the parsed info.
                Movie movie =
                        new Movie(title, imageUrl, overview, id, avgRating, releaseDate, duration);

                movies.add(i, movie);
            }
        } else {
            // Something went wrong with parsing the String of JSON data.  Can't do anything.
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
                // Cases are unique when key is average rating since they're Doubles vs Strings.
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

    /**
     * Parse out detailed info from a single Movie object.
     * @param movieString The movie from which to parse details.
     * @return The Movie object constructed from the parsed details.
     * @throws JSONException In the event there is an error converting
     *                       the passed String to a JSONObject.
     */
    public static Movie getMovieDetails(String movieString) throws JSONException {
        JSONObject movieJSON = new JSONObject(movieString);

        String title = setValueForKey(movieJSON, "title").toString();
        String imageUrl = setValueForKey(movieJSON, "poster_path").toString();
        String overview = setValueForKey(movieJSON, "overview").toString();
        String id = setValueForKey(movieJSON, "id").toString();
        String releaseDate = setValueForKey(movieJSON, "release_date").toString();
        String duration = setValueForKey(movieJSON, "runtime").toString();
        Double avgRating =
                Double.parseDouble(setValueForKey(movieJSON, "vote_average").toString());

        return new Movie(title, imageUrl, overview, id, avgRating, releaseDate, duration);
    }
}
